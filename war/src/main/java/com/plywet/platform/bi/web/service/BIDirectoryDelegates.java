package com.plywet.platform.bi.web.service;

import com.plywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.plywet.platform.bi.component.components.browse.BrowseMeta;
import com.plywet.platform.bi.core.exception.BIException;

public interface BIDirectoryDelegates {
	/**
	 * 获得父目录对象
	 * 
	 * @param repository
	 * @param id
	 * @return
	 * @throws BIException
	 */
	public BreadCrumbMeta getParentDirectories(String repository, Long id)
			throws BIException;

	/**
	 * 获得子目录对象
	 * 
	 * @param id
	 * @param browse
	 * @throws BIException
	 */
	public void getSubDirectory(String repository, Long id, BrowseMeta browse)
			throws BIException;

	/**
	 * 获得目录下面的子对象
	 * 
	 * @param id
	 * @param browse
	 * @throws BIException
	 */
	public void getSubDirectoryObject(String repository, Long id,
			BrowseMeta browse) throws BIException;
}
