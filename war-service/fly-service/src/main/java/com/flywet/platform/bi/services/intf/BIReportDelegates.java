package com.flywet.platform.bi.services.intf;

import com.flywet.platform.bi.core.exception.BIException;

public interface BIReportDelegates {

	/**
	 * 通过报表ID获得报表内容对象
	 * 
	 * @param id
	 * @return
	 * @throws BIException
	 */
	public Object[] getReportObject(Long id) throws BIException;
}
