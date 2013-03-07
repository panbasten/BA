package com.yonyou.bq8.di.web.service;

import java.util.List;

import org.apache.commons.vfs.FileObject;

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.delegates.utils.DIFileSystemCategory;
import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;
import com.yonyou.bq8.di.delegates.vo.FilesysType;

public interface DIFileSystemDelegate {

	/**
	 * 查询根目录描述信息
	 * 
	 * @param category
	 * @param rootId
	 * @return
	 * @throws DIException
	 */
	public String getRootDesc(String category, long rootId) throws DIException;

	/**
	 * 获取文件系统所有类型
	 * 
	 * @return
	 * @throws DIException
	 */
	public List<FilesysType> getFilesysTypes() throws DIException;

	/**
	 * 获取文件系统根目录
	 * 
	 * @param typeId
	 * @return
	 * @throws DIException
	 */
	public List<FilesysDirectory> getFilesysRoots(DIFileSystemCategory cate)
			throws DIException;

	/**
	 * 拼装vfs文件
	 * 
	 * @param category
	 * @param workDir
	 * @param rootId
	 * @return
	 * @throws DIException
	 */
	public FileObject composeVfsObject(String category, String workDir,
			long rootId) throws DIException;

}
