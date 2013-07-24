package com.flywet.platform.bi.component.core;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.core.exception.BIJSONException;

/**
 * 表格数据元接口
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public interface ComponentDataInterface {

	public JSONObject getFormDataJo() throws BIJSONException;

	public void construct(JSONObject json) throws BIJSONException;

	public void removeAll() throws BIJSONException;

}
