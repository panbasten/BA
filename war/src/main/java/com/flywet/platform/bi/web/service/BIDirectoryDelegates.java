package com.flywet.platform.bi.web.service;

import org.pentaho.di.repository.RepositoryDirectoryInterface;

import com.flywet.platform.bi.component.components.breadCrumb.BreadCrumbMeta;
import com.flywet.platform.bi.component.components.browse.BrowseMeta;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;

public interface BIDirectoryDelegates {
	/**
	 * 获得父目录对象
	 * 
	 * @param repository
	 * @param id
	 * @param category
	 * @return
	 * @throws BIException
	 */
	public BreadCrumbMeta getParentDirectories(String repository, Long id,
			BIDirectoryCategory category) throws BIException;

	/**
	 * 获得子目录对象
	 * 
	 * @param id
	 * @param browse
	 * @param category
	 * @throws BIException
	 */
	public void getSubDirectory(String repository, Long id, BrowseMeta browse,
			BIDirectoryCategory category) throws BIException;

	/**
	 * 获得目录下面的子对象
	 * 
	 * @param id
	 * @param browse
	 * @param category
	 * @throws BIException
	 */
	public void getSubDirectoryObject(String repository, Long id,
			BrowseMeta browse, BIDirectoryCategory category) throws BIException;

	/**
	 * 获得根目录对象
	 * 
	 * @param category
	 * @return
	 */
	public RepositoryDirectoryInterface getRootDirectory(
			BIDirectoryCategory category);

	/**
	 * 获得子目录对象
	 * 
	 * @param repository
	 * @param id
	 * @param category
	 * @return
	 * @throws BIException
	 */
	public RepositoryDirectoryInterface getDirecotry(String repository,
			long id, BIDirectoryCategory category) throws BIException;

	/**
	 * 新建一个目录
	 * 
	 * @param repository
	 * @param parentDirId
	 * @param name
	 * @param category
	 * @throws BIException
	 */
	public void newDirectoryObject(String repository, long parentDirId,
			String name, BIDirectoryCategory category) throws BIException;

	/**
	 * 删除一个目录
	 * 
	 * @param repository
	 * @param dirId
	 * @param category
	 * @throws BIException
	 */
	public void removeDirectoryObject(String repository, long dirId,
			BIDirectoryCategory category) throws BIException;
}
