package com.flywet.platform.bi.pivot.model;


/**
 * 区域对象的接口类
 * 
 * @author PeterPan
 * 
 */
public interface IRegionObject extends IPivotReport {

	public static final String PROP_NAME_REGION_OBJECT_TYPE = "type";
	public static final String PROP_NAME_REGION_DATA = "regionData";

	/**
	 * 获得区域对象的类型名称
	 * 
	 * @return
	 */
	public String getTypeName();

}
