package com.flywet.platform.bi.cust.p002.services;

import java.util.HashMap;

import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.model.ParameterContext;

public interface CYDelegates {

	/**
	 * 上传员工信息
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String uploadEmployeeInfo(String targetId,
			HashMap<String, Object> context) throws BIJSONException;
	
	/**
	 * 上传员工信息-操作
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String uploadEmployeeInfoSubmit(String targetId,
			ParameterContext context) throws BIJSONException;

}
