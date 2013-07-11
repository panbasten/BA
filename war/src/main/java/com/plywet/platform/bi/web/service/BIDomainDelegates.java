package com.plywet.platform.bi.web.service;

import com.plywet.platform.bi.core.exception.BIException;

public interface BIDomainDelegates extends BIDirectoryDelegates {
	/**
	 * 通过语义定义ID获得模型对象
	 * 
	 * @param id
	 * @return
	 * @throws BIException
	 */
	public Object[] getDomainObject(Long id) throws BIException;
}
