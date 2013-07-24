package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;

public interface BIDomainAdaptor {
	/**
	 * 通过目录ID获得子目录和旗下的语义模型对象
	 * 
	 * @param dirId
	 * @return
	 * @throws BIKettleException
	 */
	public List<Object[]> getSubDirectoryObjects(String dirId)
			throws BIKettleException;

	/**
	 * 通过ID获得语义模型对象
	 * 
	 * @param id
	 * @return
	 * @throws BIKettleException
	 */
	public Object[] getDomainObject(String id) throws BIKettleException;
}
