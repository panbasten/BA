package com.plywet.platform.bi.web.service;

import com.plywet.platform.bi.core.exception.BIException;

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
