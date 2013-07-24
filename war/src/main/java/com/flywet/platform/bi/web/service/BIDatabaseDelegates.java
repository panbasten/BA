package com.flywet.platform.bi.web.service;

import org.pentaho.di.core.database.DatabaseMeta;

import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;

public interface BIDatabaseDelegates {
	/**
	 * 获得导航的数据库项
	 * 
	 * @param repository
	 * @param browseMeta
	 * @throws BIException
	 */
	public void getNavigatorsDatabase(String repository, BrowseMeta browseMeta)
			throws BIException;

	/**
	 * 获得数据库元数据
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws BIException
	 */
	public DatabaseMeta getDatabaseMeta(String repository, Long id)
			throws BIException;

	/**
	 * 保存数据库元数据
	 * 
	 * @param repository
	 * @param databaseMeta
	 * @throws BIException
	 */
	public void saveDatabaseMeta(String repository, DatabaseMeta databaseMeta)
			throws BIException;

	/**
	 * 删除数据库元数据
	 * 
	 * @param repository
	 * @param dbName
	 * @throws BIException
	 */
	public void deleteDatabaseMeta(String repository, String dbName)
			throws BIException;

	/**
	 * 通过名称判断数据库元数据是否存在
	 * 
	 * @param repository
	 * @param dbName
	 * @return
	 * @throws BIKettleException
	 */
	public boolean existDatabaseMeta(String repository, String dbName)
			throws BIKettleException;
}
