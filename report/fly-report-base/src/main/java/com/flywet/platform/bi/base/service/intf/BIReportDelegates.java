package com.flywet.platform.bi.base.service.intf;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.services.intf.BIDirectoryDelegates;

public interface BIReportDelegates extends BIDirectoryDelegates {

	/**
	 * 通过报表ID获得报表内容对象
	 * 
	 * @param id
	 * @return
	 * @throws BIException
	 */
	public Object[] getReportObject(Long id) throws BIException;
}
