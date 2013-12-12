package com.flywet.platform.bi.smart.dao.intf;

import java.util.List;

import com.flywet.platform.bi.core.exception.BIKettleException;

public interface BISmartAdaptor {
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
