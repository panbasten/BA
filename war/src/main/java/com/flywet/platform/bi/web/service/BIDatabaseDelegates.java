package com.flywet.platform.bi.web.service;

import org.pentaho.di.core.database.DatabaseMeta;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;

public interface BIDatabaseDelegates {
	/**
	 * 获得导航的数据库项
	 * 
	 * @param browseMeta
	 * @throws BIException
	 */
	public void getNavigatorsDatabase(BrowseMeta browseMeta) throws BIException;

	/**
	 * 获得数据库元数据
	 * 
	 * @param id
	 * @return
	 * @throws BIException
	 */
	public DatabaseMeta getDatabaseMeta(Long id) throws BIException;

	/**
	 * 保存数据库元数据
	 * 
	 * @param databaseMeta
	 * @throws BIException
	 */
	public void saveDatabaseMeta(DatabaseMeta databaseMeta) throws BIException;

	/**
	 * 删除数据库元数据
	 * 
	 * @param dbName
	 * @throws BIException
	 */
	public void deleteDatabaseMeta(String dbName) throws BIException;

	/**
	 * 通过名称判断数据库元数据是否存在
	 * 
	 * @param dbName
	 * @return
	 * @throws BIKettleException
	 */
	public boolean existDatabaseMeta(String dbName) throws BIKettleException;
}
