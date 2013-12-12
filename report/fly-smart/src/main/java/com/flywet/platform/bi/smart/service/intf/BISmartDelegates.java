package com.flywet.platform.bi.smart.service.intf;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.services.intf.BIDirectoryDelegates;

public interface BISmartDelegates extends BIDirectoryDelegates {
	/**
	 * 通过语义定义ID获得模型对象
	 * 
	 * @param id
	 * @return
	 * @throws BIException
	 */
	public Object[] getSmartObject(Long id) throws BIException;
}
