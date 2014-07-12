package com.flywet.platform.bi.model;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.pentaho.di.core.ConstDB;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.Counters;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LoggingObjectInterface;
import org.pentaho.di.core.logging.LoggingObjectType;
import org.pentaho.di.core.logging.SimpleLoggingObject;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;

import com.flywet.platform.bi.rest.BIDBResource;

public class BIDatabaseConnectionDelegate {

	private static Class<?> PKG = Repository.class;

	private final Logger log = Logger.getLogger(BIDBResource.class);

	public static final LoggingObjectInterface loggingObject = new SimpleLoggingObject(
			"Database", LoggingObjectType.DATABASE, null);

	protected Database database;
	protected DatabaseMeta databaseMeta;

	protected boolean isConnect;

	protected Map<String, PreparedStatement> sqlMap;

	public BIDatabaseConnectionDelegate(DatabaseMeta databaseMeta) {
		this.databaseMeta = databaseMeta;
		this.database = new Database(loggingObject, databaseMeta);

		this.sqlMap = new HashMap<String, PreparedStatement>();
	}

	public synchronized void connect() throws KettleException {
		connect(false);
	}

	public synchronized void connect(boolean autoCommit) throws KettleException {
		try {
			database.initializeVariablesFrom(null);
			database.connect();
			setAutoCommit(autoCommit);

			isConnect = true;
		} catch (KettleException e) {
			throw new KettleException("Error connecting to the database!", e);
		}
	}

	public synchronized void disconnect() {
		try {
			for (String sql : sqlMap.keySet()) {
				PreparedStatement ps = sqlMap.get(sql);
				try {
					ps.close();
				} catch (SQLException e) {
					log.error("Error closing prepared statement: " + sql, e);
				}
			}

			if (!database.isAutoCommit())
				commit();
			isConnect = false;
		} catch (KettleException dbe) {
			log.error("Error disconnecting from database : " + dbe.getMessage());
		} finally {
			database.disconnect();
			sqlMap.clear();
		}
	}

	public synchronized void commit() throws KettleException {
		try {

			if (!database.isAutoCommit()) {
				database.commit();
			}
		} catch (KettleException dbe) {
			throw new KettleException("Unable to commit repository connection",
					dbe);
		}
	}

	public synchronized void setAutoCommit(boolean autocommit) {
		if (!autocommit)
			database.setCommit(99999999);
		else
			database.setCommit(0);
	}

	public void rollback() throws KettleDatabaseException {
		database.rollback();
	}

	/**
	 * 插入一行记录
	 * 
	 * @param tablename
	 * @param values
	 * @throws KettleException
	 */
	public synchronized void insertTableRow(String tablename,
			RowMetaAndData values) throws KettleDatabaseException {
		database.prepareInsert(values.getRowMeta(), quoteTable(tablename));
		database.setValuesInsert(values);
		database.insertRow();
		database.closeInsert();
	}

	/**
	 * 更新一条表记录
	 * 
	 * @param tablename
	 * @param idfield
	 * @param values
	 * @throws KettleException
	 */
	public synchronized void updateTableRow(String tablename, String idfield,
			RowMetaAndData values) throws KettleException {
		long id = values.getInteger(idfield, 0L);
		values.removeValue(idfield);
		String sets[] = new String[values.size()];
		for (int i = 0; i < values.size(); i++)
			sets[i] = values.getValueMeta(i).getName();
		String codes[] = new String[] { idfield };
		String condition[] = new String[] { "=" };

		database.prepareUpdate(quoteTable(tablename), codes, condition, sets);

		values.addValue(
				new ValueMeta(idfield, ValueMetaInterface.TYPE_INTEGER),
				new Long(id));

		database.setValuesUpdate(values.getRowMeta(), values.getData());
		database.updateRow();
	}

	private RowMetaAndData getParameterMetaData(ObjectId... ids)
			throws KettleException {
		RowMetaInterface parameterMeta = new RowMeta();
		Object[] parameterData = new Object[ids.length];
		for (int i = 0; i < ids.length; i++) {
			parameterMeta.addValueMeta(new ValueMeta("id" + (i + 1),
					ValueMetaInterface.TYPE_INTEGER));
			parameterData[i] = Long.valueOf(ids[i].getId());
		}
		return new RowMetaAndData(parameterMeta, parameterData);
	}

	/**
	 * 删除若干条表记录
	 * 
	 * @param sql
	 * @param ids
	 * @throws KettleException
	 */
	public synchronized void performDelete(String sql, ObjectId... ids)
			throws KettleException {
		try {
			PreparedStatement ps = sqlMap.get(sql);
			if (ps == null) {
				ps = database.prepareSQL(sql);
				sqlMap.put(sql, ps);
			}

			RowMetaAndData param = getParameterMetaData(ids);
			database.setValues(param, ps);
			ps.execute();
		} catch (SQLException e) {
			throw new KettleException("Unable to perform delete with SQL: "
					+ sql + ", ids=" + ids.toString(), e);
		}
	}

	public String quoteField(String identifier) {
		return databaseMeta.quoteField(identifier);
	}

	public String quoteTable(String table) {
		return databaseMeta.getQuotedSchemaTableCombination(null, table);
	}

	/**
	 * 获取一行记录
	 * 
	 * @param sql
	 *            查询语句
	 * @return
	 * @throws KettleDatabaseException
	 */
	public Object[] getOneRow(String sql) throws KettleDatabaseException {
		RowMetaAndData row = getOneRowWithMeta(sql);
		if (row != null) {
			return row.getData();
		}
		return null;
	}

	public Object[] getOneRow(String sql, Object[] params)
			throws KettleDatabaseException {
		RowMetaAndData row = getOneRowWithMeta(sql, params);
		if (row != null) {
			return row.getData();
		}
		return null;
	}

	public RowMetaAndData getOneRowWithMeta(String sql)
			throws KettleDatabaseException {
		return database.getOneRow(sql);
	}

	public RowMetaAndData getOneRowWithMeta(String sql, Object[] params)
			throws KettleDatabaseException {
		if (params != null) {
			RowMetaInterface rowMeta = new RowMeta();
			for (Object d : params) {
				rowMeta.addValueMeta(ConstDB.getValueMeta(d));
			}
			return database.getOneRow(sql, rowMeta, params);
		}
		return database.getOneRow(sql);
	}

	public Object[] getOneRow(String tablename, String keyfield, ObjectId id)
			throws KettleException {
		RowMetaAndData row = getOneRowWithMeta(tablename, keyfield, id);
		if (row != null) {
			return row.getData();
		}
		return null;
	}

	public RowMetaAndData getOneRowWithMeta(String tablename, String keyfield,
			ObjectId id) throws KettleException {
		String sql = "SELECT * FROM " + quoteTable(tablename) + " WHERE "
				+ quoteField(keyfield) + " = ?";
		Object[] parameterData = new Object[] { id != null ? Long.parseLong(id
				.getId()) : null, };
		return getOneRowWithMeta(sql, parameterData);
	}

	public Object[] getOneRow(String tablename, RowMetaAndData rmd)
			throws KettleException {
		RowMetaAndData row = getOneRowWithMeta(tablename, rmd);
		if (row != null) {
			return row.getData();
		}
		return null;
	}

	public RowMetaAndData getOneRowWithMeta(String tablename, RowMetaAndData rmd)
			throws KettleException {
		String sql = "SELECT * FROM " + quoteTable(tablename) + " WHERE";

		RowMetaAndData newRmd = rmd.clone();

		Object[] datas = newRmd.getData();
		String[] fieldNames = newRmd.getRowMeta().getFieldNames();
		for (int i = 0; i < datas.length; i++) {
			if (datas[i] == null) {
				sql = sql + " " + quoteField(fieldNames[i]) + " IS NULL";
				newRmd.removeValue(i);
			} else {
				sql = sql + " " + quoteField(fieldNames[i]) + " = ?";
			}
		}

		return database.getOneRow(sql, newRmd.getRowMeta(), newRmd.getData());
	}

	/**
	 * 根据sql查询并返回所有命中的记录
	 * 
	 * @param sql
	 *            查询语句
	 * @return
	 * @throws KettleDatabaseException
	 */
	public List<Object[]> getRows(String sql) throws KettleDatabaseException {
		List<Object[]> rows = database.getRows(sql, 0);
		if (rows == null || rows.isEmpty()) {
			return Collections.emptyList();
		}
		return rows;
	}

	public List<Object[]> getRows(String sql, List<Object> data)
			throws KettleDatabaseException {
		return getRows(replaceParam(sql, data));
	}

	public List<Object[]> getRows(String sql, Object data)
			throws KettleDatabaseException {
		if (data instanceof Object[]) {
			return getRows(replaceParamArray(sql, (Object[]) data));
		}
		return getRows(replaceParam(sql, data));
	}

	public List<RowMetaAndData> getRowsWithMeta(String sql)
			throws KettleDatabaseException {
		return database.getRowsWithMeta(sql, 0);
	}

	public List<RowMetaAndData> getRowsWithMeta(String sql, Object data)
			throws KettleDatabaseException {
		return getRowsWithMeta(replaceParam(sql, data));
	}

	public List<RowMetaAndData> getRowsWithMeta(String sql, List<Object> data)
			throws KettleDatabaseException {
		return getRowsWithMeta(replaceParam(sql, data));
	}

	public List<RowMetaAndData> getRowsWithMeta(String sql, Object[] data)
			throws KettleDatabaseException {
		return getRowsWithMeta(replaceParam(sql, data));
	}

	private String replaceParamArray(String sql, Object[] params) {
		if (params == null) {
			return sql;
		}
		for (Object param : params) {
			sql = replaceParam(sql, param);
		}
		return sql;
	}

	/**
	 * 简单替换sql中的参数占位符
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private String replaceParam(String sql, List<Object> params) {
		if (params == null || params.isEmpty()) {
			return sql;
		}
		for (Object param : params) {
			sql = replaceParam(sql, param);
		}
		return sql;
	}

	/**
	 * 简单替换sql中的参数占位符
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private String replaceParam(String sql, Object param) {
		if (param instanceof Integer || param instanceof Long
				|| param instanceof Short || param instanceof Float
				|| param instanceof Double) {
			String p = StringEscapeUtils.escapeSql(String.valueOf(param));
			sql = StringUtils.replaceOnce(sql, "?", p);
			return sql;
		} else if (param instanceof BigDecimal) {
			String p = StringEscapeUtils.escapeSql(param.toString());
			sql = StringUtils.replaceOnce(sql, "?", p);
			return sql;
		} else {
			String p = StringEscapeUtils.escapeSql((String) param);
			sql = StringUtils.replaceOnce(sql, "?", "'" + p + "'");
			return sql;
		}

	}

	/**
	 * 获取下一个流水号
	 * 
	 * @return
	 * @throws KettleDatabaseException
	 */
	public synchronized long getNextBatchId(String tableName, String fieldName)
			throws KettleException {
		LongObjectId id = getNextID(quoteTable(tableName),
				quoteField(fieldName));
		return id.longValue();
	}

	private synchronized LongObjectId getNextID(String tableName,
			String fieldName) throws KettleException {
		String counterName = tableName + "." + fieldName;
		Counter counter = Counters.getInstance().getCounter(counterName);
		if (counter == null) {
			LongObjectId id = getNextTableID(tableName, fieldName);
			counter = new Counter(id.longValue());
			Counters.getInstance().setCounter(counterName, counter);
			return new LongObjectId(counter.next());
		} else {
			return new LongObjectId(counter.next());
		}
	}

	private synchronized LongObjectId getNextTableID(String tablename,
			String idfield) throws KettleException {
		LongObjectId retval = null;

		RowMetaAndData r = database.getOneRow("SELECT MAX(" + idfield
				+ ") FROM " + tablename);
		if (r != null) {
			Long id = r.getInteger(0);

			if (id == null) {
				retval = new LongObjectId(1);
			} else {
				retval = new LongObjectId(id.longValue() + 1L);
			}
		}
		return retval;
	}

	/**
	 * 执行sql语句
	 * 
	 * @param rawsql
	 * @param data
	 * @throws KettleException
	 */
	public synchronized void execSql(String rawsql, Object[] data)
			throws KettleException {
		execSql(rawsql, data, true);
	}

	public synchronized void execSql(String rawsql, Object[] data,
			boolean commit) throws KettleException {
		try {
			database.execStatement(rawsql, data);
			if (commit) {
				commit();
			}
		} catch (KettleDatabaseException e) {
			log.error("exec sql exception:", e);
			rollback();
			throw e;
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param rawsql
	 * @param params
	 * @param data
	 * @throws KettleException
	 */
	public synchronized void execSql(String rawsql, RowMetaInterface params,
			Object[] data) throws KettleException {
		execSql(rawsql, params, data, true);
	}

	public synchronized void execSql(String rawsql, RowMetaInterface params,
			Object[] data, boolean commit) throws KettleException {
		try {
			database.execStatement(rawsql, params, data);
			if (commit) {
				commit();
			}
		} catch (KettleDatabaseException e) {
			log.error("exec sql exception:", e);
			rollback();
			throw e;
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @throws KettleException
	 */
	public synchronized void execSql(String sql) throws KettleException {
		execSql(sql, true);
	}

	public synchronized void execSql(String sql, boolean commit)
			throws KettleException {
		try {
			database.execStatement(sql);
			if (commit) {
				commit();
			}
		} catch (KettleDatabaseException e) {
			log.error("exec sql exception:", e);
			rollback();
			throw e;
		}
	}

}
