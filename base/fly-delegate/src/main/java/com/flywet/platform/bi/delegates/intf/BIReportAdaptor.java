package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import com.flywet.platform.bi.core.exception.BIKettleException;

public interface BIReportAdaptor {
	/**
	 * 通过目录ID获得子目录和旗下的报表对象
	 * 
	 * @param dirId
	 * @return
	 * @throws BIKettleException
	 */
	public List<Object[]> getSubDirectoryObjects(String dirId)
			throws BIKettleException;

	/**
	 * 通过ID获得报表对象
	 * 
	 * @param id
	 * @return
	 * @throws BIKettleException
	 */
	public Object[] getReportObject(String id) throws BIKettleException;
}
