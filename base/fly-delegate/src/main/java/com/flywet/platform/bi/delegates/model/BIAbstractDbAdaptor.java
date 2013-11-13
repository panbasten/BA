package com.flywet.platform.bi.delegates.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.pentaho.di.core.ConstDB;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

import com.flywet.platform.bi.core.exception.BIKettleException;

/**
 * DB资源库操作抽象基类
 * 
 * @author han
 * 
 */
public abstract class BIAbstractDbAdaptor extends BIAbstractDelegate implements
		BIDBAdaptorInterface {
	private final Logger logger = Logger.getLogger(BIAbstractDbAdaptor.class);

	public String quote(String identifier) {
		return ((KettleDatabaseRepository) repository).getRepositoryMeta()
				.getConnection().quoteField(identifier);
	}

	public String quoteTable(String table) {
		return ((KettleDatabaseRepository) repository).getRepositoryMeta()
				.getConnection().getQuotedSchemaTableCombination(null, table);
	}

	public KettleDatabaseRepository getRepository() {
		return (KettleDatabaseRepository) this.repository;
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
		return getRepository().connectionDelegate.getOneRow(sql);
	}

	public RowMetaAndData getOneRowWithMeta(String sql, Object[] params)
			throws KettleDatabaseException {
		if (params != null) {
			RowMetaInterface rowMeta = new RowMeta();
			for (Object d : params) {
				rowMeta.addValueMeta(ConstDB.getValueMeta(d));
			}
			return getRepository().connectionDelegate.getOneRow(sql, rowMeta,
					params);
		}
		return getRepository().connectionDelegate.getOneRow(sql);
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
		return getRepository().connectionDelegate.getOneRow(
				quoteTable(tablename), quote(keyfield), id);
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
		List<Object[]> rows = getRepository().connectionDelegate.getRows(sql);
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
		return getRepository().connectionDelegate.getRowsWithMeta(sql);
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

	/**
	 * 插入一行记录
	 * 
	 * @param tablename
	 * @param values
	 * @throws KettleException
	 */
	@Override
	public void insertTableRow(String tablename, RowMetaAndData values)
			throws KettleException {
		try {
			getRepository().connectionDelegate.insertTableRow(
					quoteTable(tablename), values);
		} catch (KettleException e) {
			logger.error("insert table row exception:", e);
			throw e;
		}
	}

	/**
	 * 更新一条表记录
	 * 
	 * @param tablename
	 * @param idfield
	 * @param values
	 * @throws KettleException
	 */
	@Override
	public void updateTableRow(String tablename, String idfield,
			RowMetaAndData values) throws KettleException {
		try {
			getRepository().connectionDelegate.updateTableRow(
					quoteTable(tablename), quote(idfield), values);
		} catch (KettleException e) {
			logger.error("insert table row exception:", e);
			throw e;
		}
	}

	/**
	 * 删除若干条表记录
	 * 
	 * @param sql
	 * @param ids
	 * @throws KettleException
	 */
	@Override
	public void performDelete(String sql, ObjectId... ids)
			throws KettleException {
		try {
			getRepository().connectionDelegate.performDelete(sql, ids);
		} catch (KettleException e) {
			logger.error("perform delete exception:", e);
			throw e;
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param rawsql
	 * @param data
	 * @throws KettleDatabaseException
	 */
	@Override
	public void execSql(String rawsql, Object[] data)
			throws KettleDatabaseException {
		execSql(rawsql, data, true);
	}

	@Override
	public void execSql(String rawsql, Object[] data, boolean commit)
			throws KettleDatabaseException {
		try {
			getRepository().connectionDelegate.getDatabase().execStatement(
					rawsql, data);
			if (commit) {
				getRepository().connectionDelegate.getDatabase().commit();
			}
		} catch (KettleDatabaseException e) {
			logger.error("exec sql exception:", e);
			getRepository().connectionDelegate.getDatabase().rollback();
			throw e;
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param rawsql
	 * @param params
	 * @param data
	 * @throws KettleDatabaseException
	 */
	@Override
	public void execSql(String rawsql, RowMetaInterface params, Object[] data)
			throws KettleDatabaseException {
		execSql(rawsql, params, data, true);
	}

	@Override
	public void execSql(String rawsql, RowMetaInterface params, Object[] data,
			boolean commit) throws KettleDatabaseException {
		try {
			getRepository().connectionDelegate.getDatabase().execStatement(
					rawsql, params, data);
			if (commit) {
				getRepository().connectionDelegate.getDatabase().commit();
			}
		} catch (KettleDatabaseException e) {
			logger.error("exec sql exception:", e);
			getRepository().connectionDelegate.getDatabase().rollback();
			throw e;
		}
	}

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @throws KettleDatabaseException
	 */
	@Override
	public void execSql(String sql) throws KettleDatabaseException {
		execSql(sql, true);
	}

	@Override
	public void execSql(String sql, boolean commit)
			throws KettleDatabaseException {
		try {
			getRepository().connectionDelegate.getDatabase().execStatement(sql);
			if (commit) {
				getRepository().connectionDelegate.getDatabase().commit();
			}
		} catch (KettleDatabaseException e) {
			logger.error("exec sql exception:", e);
			getRepository().connectionDelegate.getDatabase().rollback();
			throw e;
		}
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
	 * @throws BIKettleException
	 */
	@Override
	public long getNextBatchId(String tableName, String fieldName)
			throws BIKettleException {
		try {
			LongObjectId batchId = getRepository().connectionDelegate
					.getNextID(quoteTable(tableName), quote(fieldName));
			return batchId.longValue();
		} catch (KettleException e) {
			logger.error("get batchid exception:", e);
			throw new BIKettleException(e.getMessage());
		}
	}

}
