package com.flywet.platform.bi.smart.service.intf;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.services.intf.BIDirectoryDelegates;
import com.flywet.platform.bi.smart.model.BISmartMeta;

public interface BISmartDelegates extends BIDirectoryDelegates {
	/**
	 * 通过语义定义ID获得模型对象
	 * 
	 * @param id
	 * @return
	 * @throws BIException
	 */
	public BISmartMeta getSmartObject(Long id) throws BIException;
}
