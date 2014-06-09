package com.flywet.platform.bi.pivot.model;

import com.flywet.platform.bi.core.exception.BIException;
import com.tonbeller.wcf.controller.RequestContext;

public interface IPivotReport extends IJSONObjectable {

	/**
	 * 初始化区域模型
	 * 
	 * @throws BIException
	 */
	public void init(RequestContext context) throws BIException;

	/**
	 * 通过名称获得对象
	 * 
	 * @throws BIException
	 */
	public Object findByName(String name) throws BIException;

}
