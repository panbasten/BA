package com.yonyou.bq8.di.component.components.base;

import org.drools.util.StringUtils;
import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.SimpleComponentMeta;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

/**
 * 表单设置
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class FormMeta extends SimpleComponentMeta {

	private String action;

	private String method = HTML.ATTR_METHOD_POST;

	private String target;

	@Override
	public void removeAll() {
		super.removeAll();
		this.action = null;
		this.method = HTML.ATTR_METHOD_POST;
		this.target = null;
	}

	/**
	 * 得到表单的JSON代码
	 * 
	 * @return
	 * @throws ImetaFormException
	 */
	@Override
	public JSONObject getFormJo() throws DIJSONException {
		this.addAttribute(HTML.ATTR_ACTION, action);
		this.addAttribute(HTML.ATTR_METHOD, method);
		if (!StringUtils.isEmpty(target)) {
			this.addAttribute(HTML.ATTR_TARGET, target);
		}
		return super.getFormJo();
	}

	@Override
	public String[] getAttributesName() {
		return HTML.FORM_ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BASE_FORM;
	}

	public String getAction() {
		return action;
	}

	public FormMeta setAction(String action) {
		this.action = action;
		return this;
	}

	public String getMethod() {
		return method;
	}

	public FormMeta setMethod(String method) {
		this.method = method;
		return this;
	}

	public String getTarget() {
		return target;
	}

	public FormMeta setTarget(String target) {
		this.target = target;
		return this;
	}

}
