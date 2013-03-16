package com.plywet.platform.bi.web.service;

import org.pentaho.di.core.database.DatabaseMeta;

import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.exceptions.BIKettleException;

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
	 * @throws BIKettleException
	 */
	public DatabaseMeta getDatabaseMeta(String repository, Long id)
			throws BIKettleException;
}
