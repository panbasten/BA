package com.plywet.platform.bi.component.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;

import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.core.utils.ArrayUtils;
import com.plywet.platform.bi.core.utils.JSONUtils;

/**
 * 简单表单操作对象类
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public abstract class SimpleComponentMeta extends BaseComponentMeta implements
		ComponentMetaInterface {

	/**
	 * 样式
	 */
	private List<String> clazz;

	/**
	 * 内嵌样式
	 */
	private Map<String, String> style;

	/**
	 * 状态
	 */
	private Map<String, Boolean> state;

	/**
	 * 需要绑定的页面事件
	 */
	private Map<String, String> events;

	@Override
	public void removeAll() {
		super.removeAll();
		this.clazz = null;
		this.style = null;
		this.state = null;
		this.events = null;
	}

	@Override
	public void append(ComponentMetaInterface content) throws BIJSONException {

	}

	/**
	 * 得到表单的JSON代码
	 * 
	 * @return
	 * @throws BIJSONException
	 */
	@Override
	public JSONObject getFormJo() throws BIJSONException {
		try {
			if (clazz != null && clazz.size() > 0) {
				super.addExtendAttribute(HTML.ATTR_CLASS, ArrayUtils.join(
						clazz, " "));
			}
			if (style != null && style.size() > 0) {
				String styleStr = "";
				for (String key : style.keySet()) {
					if (!StringUtils.isEmpty(style.get(key))) {
						styleStr += key;
						styleStr += ":";
						styleStr += style.get(key);
						styleStr += ";";
					}
				}
				super.addExtendAttribute(HTML.ATTR_STYLE, styleStr);
			}

			if (state != null && state.size() > 0) {
				String stateStr = "";
				for (String key : state.keySet()) {
					if (state.get(key)) {
						stateStr += key;
						stateStr += " ";
					}
				}
				super.addExtendAttribute(HTML.ATTR_STYLE, stateStr.trim());
			}

			if (events != null)
				super.addExtendAttribute(HTML.ATTR_EVENTS, JSONUtils
						.convertToJSONObject(events));

			return super.getFormJo();
		} catch (BIJSONException e) {
			throw e;
		} catch (Exception e) {
			throw new BIJSONException(e);
		}
	}

	private void checkState() {
		if (state == null) {
			state = new HashMap<String, Boolean>();
		}
	}

	/**
	 * 定义如何是否有效
	 * 
	 * @param disabled
	 */
	public void setDisabled(boolean disabled) {
		checkState();
		state.put(HTML.ATTR_STATE_DISABLED, disabled);
	}

	public void disabled() {
		setDisabled(true);
	}

	/**
	 * 定义只读
	 * 
	 * @param readonly
	 */
	public void setReadonly(boolean readonly) {
		checkState();
		state.put(HTML.ATTR_STATE_READONLY, readonly);
	}

	public void readonly() {
		setReadonly(true);
	}

	/**
	 * 定义是否显示
	 * 
	 * @param display
	 */
	public void setActive(boolean active) {
		checkState();
		state.put(HTML.ATTR_STATE_ACTIVE, active);
	}

	public void active() {
		setActive(true);
	}

	/**
	 * 定义唯一
	 * 
	 * @param key
	 *            主要标签
	 * @param value
	 *            标签值
	 */
	public void addStyle(String key, String value) {
		if (style == null) {
			style = new HashMap<String, String>();
		}
		style.put(key, value);
	}

	/**
	 * 定义添加类型
	 * 
	 * @param clazz
	 */
	public void addClass(String clazz) {
		if (this.clazz == null) {
			this.clazz = new ArrayList<String>();
		}
		this.clazz.add(clazz);
	}

	public void addEvent(String event, String callback) {
		if (events == null) {
			events = new HashMap<String, String>();
		}
		events.put(event, callback);
	}

}
