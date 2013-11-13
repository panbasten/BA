package com.flywet.platform.bi.delegates.model;

import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.repository.ObjectId;

import com.flywet.platform.bi.core.exception.BIKettleException;

public interface BIDBAdaptorInterface extends BIAdaptorInterface {
	/**
	 * 插入一行记录
	 * 
	 * @param tablename
	 * @param values
	 * @throws KettleException
	 */
	public void insertTableRow(String tablename, RowMetaAndData values)
			throws KettleException;

	/**
	 * 更新一条表记录
	 * 
	 * @param tablename
	 * @param idfield
	 * @param values
	 * @throws KettleException
	 */
	public void updateTableRow(String tablename, String idfield,
			RowMetaAndData values) throws KettleException;

	/**
	 * 删除若干条表记录
	 * 
	 * @param sql
	 * @param ids
	 * @throws KettleException
	 */
	public void performDelete(String sql, ObjectId... ids)
			throws KettleException;

	/**
	 * 执行sql语句
	 * 
	 * @param rawsql
	 * @param data
	 * @throws KettleDatabaseException
	 */
	public void execSql(String rawsql, Object[] data)
			throws KettleDatabaseException;

	public void execSql(String rawsql, Object[] data, boolean commit)
			throws KettleDatabaseException;

	/**
	 * 执行sql语句
	 * 
	 * @param rawsql
	 * @param params
	 * @param data
	 * @throws KettleDatabaseException
	 */
	public void execSql(String rawsql, RowMetaInterface params, Object[] data)
			throws KettleDatabaseException;

	public void execSql(String rawsql, RowMetaInterface params, Object[] data,
			boolean commit) throws KettleDatabaseException;

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @throws KettleDatabaseException
	 */
	public void execSql(String sql) throws KettleDatabaseException;

	public void execSql(String sql, boolean commit)
			throws KettleDatabaseException;

	/**
	 * 获取下一个流水号
	 * 
	 * @return
	 * @throws BIKettleException
	 */
	public long getNextBatchId(String tableName, String fieldName)
			throws BIKettleException;
}
