package com.flywet.platform.bi.pivot.model;

import com.flywet.platform.bi.core.exception.BIException;
import com.tonbeller.wcf.controller.RequestContext;

public interface IPivotComponent {

	/**
	 * 初始化报表模型
	 * 
	 * @param context
	 * @throws BIException
	 */
	public void init(RequestContext context) throws BIException;
	
	
}
