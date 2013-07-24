package com.flywet.platform.bi.web.service;

import java.util.List;

import org.apache.commons.vfs.FileObject;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.enums.BIFileSystemCategory;
import com.flywet.platform.bi.delegates.vo.FilesysDirectory;
import com.flywet.platform.bi.delegates.vo.FilesysType;

public interface BIFileSystemDelegate {

	/**
	 * 查询根目录描述信息
	 * 
	 * @param category
	 * @param rootId
	 * @return
	 * @throws BIException
	 */
	public String getRootDesc(String category, long rootId) throws BIException;

	/**
	 * 获取文件系统所有类型
	 * 
	 * @return
	 * @throws BIException
	 */
	public List<FilesysType> getFilesysTypes() throws BIException;

	/**
	 * 获取文件系统根目录
	 * 
	 * @param typeId
	 * @return
	 * @throws BIException
	 */
	public List<FilesysDirectory> getFilesysRoots(BIFileSystemCategory cate)
			throws BIException;

	/**
	 * 通过ID获得文件系统目录
	 * 
	 * @param category
	 * @param rootId
	 * @return
	 * @throws BIException
	 */
	public FilesysDirectory getFilesysObject(String category, long rootId)
			throws BIException;

	/**
	 * 更新文件系统目录
	 * 
	 * @param category
	 * @param dir
	 * @throws BIException
	 */
	public void updateFilesysObject(String category, FilesysDirectory dir)
			throws BIException;

	/**
	 * 创建文件系统目录
	 * 
	 * @param category
	 * @param dir
	 * @throws BIException
	 */
	public void createFilesysObject(String category, FilesysDirectory dir)
			throws BIException;

	/**
	 * 移出文件系统目录
	 * 
	 * @param category
	 * @param id
	 * @throws BIException
	 */
	public void removeFilesysObject(String category, String id)
			throws BIException;

	/**
	 * 拼装vfs文件
	 * 
	 * @param category
	 * @param workDir
	 * @param rootId
	 * @return
	 * @throws BIException
	 */
	public FileObject composeVfsObject(String category, String workDir,
			long rootId) throws BIException;

}
