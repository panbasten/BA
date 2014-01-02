package com.flywet.platform.bi.pivot.service.intf;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.PivotReport;

/**
 * 多维报表代理服务接口
 * 
 * @author PeterPan
 * 
 */
public interface BIPivotDelegates {

	/**
	 * 通过透视表对象，进行查询
	 * 
	 * @param pr
	 * @return
	 * @throws BIException
	 */
	public JSONObject query(PivotReport pr) throws BIException;

}
