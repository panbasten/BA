package com.yonyou.bq8.di.web.service;

import com.yonyou.bq8.di.component.components.breadCrumb.BreadCrumbMeta;
import com.yonyou.bq8.di.component.components.browse.BrowseMeta;
import com.yonyou.bq8.di.core.exception.DIException;

public interface DIDirectoryDelegates {
	/**
	 * 获得父目录对象
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws DIException
	 */
	public BreadCrumbMeta getParentDirectories(String repository, Long id)
			throws DIException;

	/**
	 * 获得子目录对象
	 * 
	 * @param id
	 * @param browse
	 * @throws DIException
	 */
	public void getSubDirectory(String repository, Long id, BrowseMeta browse)
			throws DIException;

	/**
	 * 获得目录下面的子对象
	 * 
	 * @param id
	 * @param browse
	 * @throws DIException
	 */
	public void getSubDirectoryObject(String repository, Long id,
			BrowseMeta browse) throws DIException;
}
