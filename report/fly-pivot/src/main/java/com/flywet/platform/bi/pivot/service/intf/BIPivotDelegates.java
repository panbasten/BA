package com.flywet.platform.bi.pivot.service.intf;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.core.exception.BIException;

/**
 * 多维报表代理服务接口
 * 
 * @author PeterPan
 * 
 */
public interface BIPivotDelegates {

	public JSONObject queryMdx() throws BIException;

}
