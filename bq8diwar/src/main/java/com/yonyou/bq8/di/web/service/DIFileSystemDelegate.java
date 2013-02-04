package com.yonyou.bq8.di.web.service;

import java.util.List;

import org.apache.commons.vfs.FileObject;

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.delegates.vo.DIHost;
import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;
import com.yonyou.bq8.di.delegates.vo.FilesysType;

public interface DIFileSystemDelegate {
	/**
	 * 获取所有主机信息
	 * @return
	 * @throws DIException
	 */
	public List<DIHost> getAllHost() throws DIException;
	
	/**
	 * 根据category查询主机信息
	 * @param category
	 * @return
	 * @throws DIException
	 */
	public List<DIHost> getHostByCategory(String category) throws DIException;
	
	/**
	 * 根据id获取主机信息
	 * @param id
	 * @return
	 * @throws DIException
	 */
	public DIHost getFtphostById(long id) throws DIException;
	
	/**
	 * 新增主机
	 * @param host
	 * @throws DIException
	 */
	public void addHost(DIHost host) throws DIException;
	
	/**
	 * 更新主机信息
	 * @param host
	 * @throws DIException
	 */
	public void updateHost(DIHost host) throws DIException;
	
	/**
	 * 删除主机
	 * @param id
	 * @throws DIException
	 */
	public void delHost(long id) throws DIException;
	
	/**
	 * 获取文件系统所有类型
	 * @return
	 * @throws DIException
	 */
	public List<FilesysType> getFilesysTypes() throws DIException;
	
	/**
	 * 获取服务器本地文件系统根目录
	 * @return
	 * @throws DIException
	 */
	public List<FilesysDirectory> getLocalRoots() throws DIException;
	
	/**
	 * 根据id获取服务器本地根目录信息
	 * @param id
	 * @return
	 * @throws DIException
	 */
	public FilesysDirectory getLocalRootById(long id) throws DIException;
	
	/**
	 * 新增服务器本地文件根目录信息
	 * @param directory
	 * @throws DIException
	 */
	public void addLocalRoot(FilesysDirectory directory) throws DIException;
	
	/**
	 * 更新服务器本地文件根目录信息
	 * @param directory
	 * @throws DIException
	 */
	public void updateLocalRoot(FilesysDirectory directory) throws DIException;
	
	/**
	 * 删除本地根目录信息
	 * @param id
	 * @throws DIException
	 */
	public void deleteLocalRoot(long id) throws DIException;
	
	/**
	 * 拼装vfs文件
	 * @param category
	 * @param workDir
	 * @param rootId
	 * @return
	 * @throws DIException
	 */
	public FileObject composeVfsObject(String category, String workDir,long rootId) throws DIException;
	
	/**
	 * 查询根目录描述信息
	 * @param category
	 * @param rootId
	 * @return
	 * @throws DIException
	 */
	public String getRootDesc(String category, long rootId) throws DIException;
	
}
