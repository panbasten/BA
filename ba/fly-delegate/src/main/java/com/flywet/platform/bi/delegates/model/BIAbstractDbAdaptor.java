package com.flywet.platform.bi.delegates.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;

/**
 * DB资源库操作抽象基类
 * 
 * @author han
 * 
 */
public abstract class BIAbstractDbAdaptor extends BIAbstractDelegate {
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
		List<Object[]> rows = getRepository().connectionDelegate
				.getRows(sql, 1);
		if (rows == null || rows.isEmpty()) {
			return null;
		}
		return rows.get(0);
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

	public List<RowMetaAndData> getRowsWithMeta(String sql)
			throws KettleDatabaseException {
		return getRepository().connectionDelegate.getRowsWithMeta(sql);
	}

	/**
	 * 执行sql语句
	 * 
	 * @param rawsql
	 * @param params
	 * @param data
	 * @throws KettleDatabaseException
	 */
	public void execSql(String rawsql, RowMetaInterface params, Object[] data)
			throws KettleDatabaseException {
		execSql(rawsql, params, data, true);
	}

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
	 * @param rawsql
	 * @param data
	 * @throws KettleDatabaseException
	 */
	public void execSql(String rawsql, Object[] data)
			throws KettleDatabaseException {
		execSql(rawsql, data, true);
	}

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
	 * @param sql
	 * @throws KettleDatabaseException
	 */
	public void execSql(String sql) throws KettleDatabaseException {
		execSql(sql, true);
	}

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

	/**
	 * 简单替换sql中的参数占位符
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public String replaceParam(String sql, List<String> params) {
		if (params == null || params.isEmpty()) {
			return sql;
		}
		for (String param : params) {
			param = StringEscapeUtils.escapeSql(param);
			sql = StringUtils.replaceOnce(sql, "?", param);
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
	public String replaceParam(String sql, String param) {
		if (StringUtils.isBlank(param)) {
			return sql;
		}
		param = StringEscapeUtils.escapeSql(param);
		sql = StringUtils.replaceOnce(sql, "?", param);
		return sql;
	}

	/**
	 * 获取下一个流水号
	 * 
	 * @return
	 * @throws BIKettleException
	 */
	public long getNextBatchId(String tableName, String fieldName)
			throws BIKettleException {
		try {
			LongObjectId batchId = getRepository().connectionDelegate
					.getNextID(tableName, fieldName);
			return batchId.longValue();
		} catch (KettleException e) {
			logger.error("get batchid exception:", e);
			throw new BIKettleException(e.getMessage());
		}
	}

}
