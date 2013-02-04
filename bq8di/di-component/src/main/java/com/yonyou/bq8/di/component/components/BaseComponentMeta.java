package com.yonyou.bq8.di.component.components;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.pentaho.di.i18n.BaseMessages;

import com.yonyou.bq8.di.component.core.ComponentDataInterface;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.core.utils.JSONUtils;

/**
 * 表格基本元
 * 
 * @author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public abstract class BaseComponentMeta implements ComponentMetaInterface {

	private static Class<?> PKG = BaseComponentMeta.class;

	private String id;

	/**
	 * 属性
	 */
	private Map<String, Object> attributes;

	/**
	 * 用于描述附加属性
	 */
	private Object data;

	/**
	 * 替换html串
	 */
	private String html;

	/**
	 * 初始化操作
	 */
	private String initScript;

	/**
	 * 完成后操作
	 */
	private String completeScript;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getInitScript() {
		return initScript;
	}

	public void setInitScript(String initScript) {
		this.initScript = initScript;
	}

	public String getCompleteScript() {
		return completeScript;
	}

	public void setCompleteScript(String completeScript) {
		this.completeScript = completeScript;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 得到表单的JSON代码
	 * 
	 * @return
	 * @throws ImetaFormException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormJo() throws DIJSONException {
		try {
			JSONObject formJo = new JSONObject();

			formJo.put(HTML.TAG_COMPONENT_TYPE, getComponentType());

			if (!StringUtils.isEmpty(id))
				formJo.put(HTML.ATTR_ID, id);
			if (!StringUtils.isEmpty(initScript))
				formJo.put(HTML.TAG_INIT_SCRIPT, initScript);
			if (!StringUtils.isEmpty(completeScript))
				formJo.put(HTML.TAG_COMPLETE_SCRIPT, completeScript);
			if (!StringUtils.isEmpty(html)) {
				formJo.put(HTML.TAG_HTML, html);
			}

			if (this.attributes != null) {
				formJo.put(HTML.TAG_ATTRITUDES, JSONUtils
						.convertToJSONObject(this.attributes));
			}

			if (this.data != null) {
				if (this.data instanceof ComponentDataInterface) {
					formJo.put(HTML.TAG_DATA,
							((ComponentDataInterface) this.data)
									.getFormDataJo());
				} else {
					formJo.put(HTML.TAG_DATA, JSONUtils.convert(this.data));
				}
			}

			return formJo;
		} catch (Exception ex) {
			throw new DIJSONException(BaseMessages.getString(PKG,
					"IForm.CreateJSON.Error"), ex);
		}
	}

	public Object getAttribute(String key) {
		if (this.attributes != null && key != null) {
			return this.attributes.get(key);
		}
		return null;
	}

	public boolean hasAttribute(String key) {
		if (this.attributes != null && key != null) {
			return this.attributes.containsKey(key);
		}
		return false;
	}

	public void addAttributes(Object[]... obj) throws DIJSONException {
		try {
			if (obj != null) {
				for (Object[] o : obj) {
					addExtendAttribute(checkAttributeName((String) o[0]), o[1]);
				}
			}
		} catch (DIJSONException e) {
			throw e;
		} catch (Exception e) {
			throw new DIJSONException(BaseMessages.getString(PKG,
					"IForm.AddAttributesJSON.Error"));
		}
	}

	public void addAttribute(String key, Object obj) throws DIJSONException {
		addExtendAttribute(checkAttributeName(key), obj);
	}

	public void addExtendAttribute(String key, Object obj) {
		if (this.attributes == null) {
			this.attributes = new HashMap<String, Object>();
		}
		this.attributes.put(key, obj);
	}

	@SuppressWarnings("unchecked")
	public void addExtendAttributes(Map map) {
		if (this.attributes == null) {
			this.attributes = new HashMap<String, Object>();
		}
		this.attributes.putAll(map);
	}

	private String checkAttributeName(String name) throws DIJSONException {
		if (getAttributesName() != null) {
			for (String n : getAttributesName()) {
				if (n.equalsIgnoreCase(name)) {
					return n;
				}
			}
		} else {
			return name;
		}
		throw new DIJSONException(BaseMessages.getString(PKG,
				"IForm.AddAttributeJSON.Error", name));
	}

	@Override
	public String toString() {
		try {
			return getFormJo().toString();
		} catch (DIJSONException e) {
			return BaseMessages.getString(PKG, "IForm.CreateJSON.Error");
		}
	}

	@Override
	public void removeAll() {
		this.attributes = null;
		this.data = null;
		this.html = null;
		this.initScript = null;
		this.completeScript = null;
	}

	@Override
	public void appendTo(ComponentMetaInterface content) throws DIJSONException {
		content.append(this);
	}

	@Override
	public void append(ComponentMetaInterface content) throws DIJSONException {
	}

	public abstract String getComponentType();

	public abstract String[] getAttributesName();

}
