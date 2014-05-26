package com.flywet.platform.bi.pivot.model;

import org.json.simple.JSONArray;

import com.flywet.platform.bi.core.exception.BIException;
import com.tonbeller.wcf.controller.RequestContext;

public interface IJSONArrayable extends IJSONable {
	/**
	 * 获得渲染的JSONArray对象
	 * 
	 * @param context
	 * @return
	 * @throws BIException
	 */
	public JSONArray renderJa(RequestContext context) throws BIException;
}
