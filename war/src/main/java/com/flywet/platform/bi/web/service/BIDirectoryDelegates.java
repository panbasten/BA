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
	 * @param id
	 * @param category
	 * @return
	 * @throws BIException
	 */
	public BreadCrumbMeta getParentDirectories(Long id,
			BIDirectoryCategory category) throws BIException;

	/**
	 * 获得子目录对象
	 * 
	 * @param id
	 * @param browse
	 * @param category
	 * @throws BIException
	 */
	public void getSubDirectory(Long id, BrowseMeta browse,
			BIDirectoryCategory category) throws BIException;

	/**
	 * 获得目录下面的子对象
	 * 
	 * @param id
	 * @param browse
	 * @param category
	 * @throws BIException
	 */
	public void getSubDirectoryObject(Long id, BrowseMeta browse,
			BIDirectoryCategory category) throws BIException;

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
	 * @param id
	 * @param category
	 * @return
	 * @throws BIException
	 */
	public RepositoryDirectoryInterface getDirecotry(long id,
			BIDirectoryCategory category) throws BIException;

	/**
	 * 新建一个目录
	 * 
	 * @param parentDirId
	 * @param name
	 * @param category
	 * @throws BIException
	 */
	public void newDirectoryObject(long parentDirId, String name,
			BIDirectoryCategory category) throws BIException;

	/**
	 * 编辑一个目录
	 * 
	 * @param parentDirId
	 * @param dirId
	 * @param name
	 * @param category
	 * @throws BIException
	 */
	public void editDirectoryObject(long parentDirId, long dirId, String name,
			BIDirectoryCategory category) throws BIException;

	/**
	 * 删除一个目录
	 * 
	 * @param dirId
	 * @param category
	 * @throws BIException
	 */
	public void removeDirectoryObject(long dirId, BIDirectoryCategory category)
			throws BIException;
}
