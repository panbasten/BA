package com.flywet.platform.bi.rest;

import com.flywet.platform.bi.core.exception.BIException;

/**
 * 编辑器资源接口
 * 
 * @author PeterPan
 * 
 */
public interface EditorResourceInterface {

	public static final String EDITOR_TEMPLATE_PREFIX = "editor/editor/editor_";

	/**
	 * 加载编辑器
	 * 
	 * @return
	 * @throws BIException
	 */
	public String loadEditor() throws BIException;

}
