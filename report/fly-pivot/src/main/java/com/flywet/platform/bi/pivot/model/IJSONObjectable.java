package com.flywet.platform.bi.pivot.model;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.core.exception.BIException;
import com.tonbeller.wcf.controller.RequestContext;

public interface IJSONObjectable extends IJSONable {
	/**
	 * 获得渲染的JSONObject对象
	 * 
	 * @param context
	 * @return
	 * @throws BIException
	 */
	public JSONObject renderJo(RequestContext context) throws BIException;
}
