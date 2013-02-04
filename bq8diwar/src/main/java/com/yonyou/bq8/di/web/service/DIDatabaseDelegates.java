package com.yonyou.bq8.di.web.service;

import org.pentaho.di.core.database.DatabaseMeta;

import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIDatabaseDelegates {
	/**
	 * 获得导航的数据库项
	 * 
	 * @param repository
	 * @param browseMeta
	 * @throws DIException
	 */
	public void getNavigatorsDatabase(String repository, BrowseMeta browseMeta)
			throws DIException;

	/**
	 * 获得数据库元数据
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws DIKettleException
	 */
	public DatabaseMeta getDatabaseMeta(String repository, Long id)
			throws DIKettleException;
}
