package com.yonyou.bq8.di.component.core;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.core.exception.DIJSONException;

/**
 * 表格数据元接口
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public interface ComponentDataInterface {

	public JSONObject getFormDataJo() throws DIJSONException;

	public void construct(JSONObject json) throws DIJSONException;

	public void removeAll() throws DIJSONException;
	
}
