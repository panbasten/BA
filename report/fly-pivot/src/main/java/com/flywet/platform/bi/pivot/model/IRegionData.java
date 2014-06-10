package com.flywet.platform.bi.pivot.model;

import com.flywet.platform.bi.core.exception.BIException;
import com.tonbeller.wcf.controller.RequestContext;

/**
 * 区域的数据对象
 * 
 * @author PeterPan
 * 
 */
public interface IRegionData extends IPivotReport {

	public static final String REGION_DATA_TYPE = "type";

	public static final String PROP_NAME_DATA = "data";

	public static final String PROP_NAME_REF = "_ref";

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
