package com.yonyou.bq8.di.component.core;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.BaseComponentMeta;
import com.yonyou.bq8.di.core.exception.DIJSONException;

/**
 * 表单操作接口类
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public interface ComponentMetaInterface {
	
	/**
	 * 得到表单对象的JSON表达式
	 * 
	 * @return
	 */
	public JSONObject getFormJo() throws DIJSONException;

	/**
	 * 向当前表单对象中添加子元素
	 * 
	 * @param content
	 * @throws ImetaFormException
	 */
	public void append(ComponentMetaInterface content) throws DIJSONException;

	/**
	 * 清空子元素
	 * 
	 * @throws ImetaFormException
	 */
	public void removeAll() throws DIJSONException;

	/**
	 * 将该表单对象添加到目标表单对象中
	 * 
	 * @param content
	 * @throws ImetaFormException
	 */
	public void appendTo(ComponentMetaInterface content) throws DIJSONException;

	/**
	 * 字符串化
	 * 
	 * @return
	 */
	public String toString();
	
}
