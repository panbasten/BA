package com.plywet.platform.bi.delegates.intf;

import java.util.List;

import com.plywet.platform.bi.delegates.vo.FilesysDirectory;
import com.plywet.platform.bi.exceptions.BIKettleException;

public interface BIFsAdaptor {

	/**
	 * 获得该FS类型的所有第一级目录对象
	 * 
	 * @return
	 * @throws BIKettleException
	 */
	public List<FilesysDirectory> getRootDirectories() throws BIKettleException;

	/**
	 * 通过ID获得该FS类型的目录对象
	 * 
	 * @param id
	 * @return
	 * @throws BIKettleException
	 */
	public FilesysDirectory getRootDirectoryById(long id)
			throws BIKettleException;

	/**
	 * 添加一个根目录对象
	 * 
	 * @param directory
	 * @throws BIKettleException
	 */
	public void addRootDirectory(FilesysDirectory directory)
			throws BIKettleException;

	/**
	 * 更新一个根目录对象
	 * 
	 * @param directory
	 * @throws BIKettleException
	 */
	public void updateRootDirectory(FilesysDirectory directory)
			throws BIKettleException;

	/**
	 * 删除一个根目录对象
	 * 
	 * @param id
	 * @throws BIKettleException
	 */
	public void deleteRootDirectory(long id) throws BIKettleException;

}
