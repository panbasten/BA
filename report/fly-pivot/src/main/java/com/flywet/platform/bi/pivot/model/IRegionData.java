package com.flywet.platform.bi.pivot.model;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.core.exception.BIException;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * 区域的数据对象
 * 
 * @author PeterPan
 * 
 */
public interface IRegionData {

	public static final String REGION_DATA_TYPE = "type";
	
	public static final String PROP_NAME_DATA = "data";

	/**
	 * 初始化数据对象
	 * 
	 * @throws BIException
	 */
	public void init() throws BIException;

	/**
	 * 获得渲染的JSONObject对象
	 * 
	 * @param context
	 * @return
	 * @throws BIException
	 */
	public JSONObject renderJo(RequestContext context) throws BIException;

	/**
	 * 获得区域对象的类型名称
	 * 
	 * @return
	 */
	public String getTypeName();

}
