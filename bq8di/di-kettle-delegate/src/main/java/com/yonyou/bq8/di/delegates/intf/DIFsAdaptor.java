package com.yonyou.bq8.di.delegates.intf;

import java.util.List;

import com.yonyou.bq8.di.delegates.vo.FilesysDirectory;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIFsAdaptor {

	/**
	 * 获得该FS类型的所有第一级目录对象
	 * 
	 * @return
	 * @throws DIKettleException
	 */
	public List<FilesysDirectory> getRootDirectories() throws DIKettleException;

	/**
	 * 通过ID获得该FS类型的目录对象
	 * 
	 * @param id
	 * @return
	 * @throws DIKettleException
	 */
	public FilesysDirectory getRootDirectoryById(long id)
			throws DIKettleException;

	/**
	 * 添加一个根目录对象
	 * 
	 * @param directory
	 * @throws DIKettleException
	 */
	public void addRootDirectory(FilesysDirectory directory)
			throws DIKettleException;

	/**
	 * 更新一个根目录对象
	 * 
	 * @param directory
	 * @throws DIKettleException
	 */
	public void updateRootDirectory(FilesysDirectory directory)
			throws DIKettleException;

	/**
	 * 删除一个根目录对象
	 * 
	 * @param id
	 * @throws DIKettleException
	 */
	public void deleteRootDirectory(long id) throws DIKettleException;

}
