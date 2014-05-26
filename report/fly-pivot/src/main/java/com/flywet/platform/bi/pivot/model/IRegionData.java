package com.flywet.platform.bi.pivot.model;

import com.flywet.platform.bi.core.exception.BIException;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * 区域的数据对象
 * 
 * @author PeterPan
 * 
 */
public interface IRegionData extends IJSONObjectable {

	public static final String REGION_DATA_TYPE = "type";

	public static final String PROP_NAME_DATA = "data";

	/**
	 * 初始化数据对象
	 * 
	 * @throws BIException
	 */
	public void init(RequestContext context) throws BIException;

	/**
	 * 刷新数据
	 * 
	 * @param context
	 * @throws BIException
	 */
	public void flush(RequestContext context) throws BIException;

	/**
	 * 获得区域对象的类型名称
	 * 
	 * @return
	 */
	public String getTypeName();

}
