package com.flywet.platform.bi.component.components;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

/**
 * 复杂表格元
 * 
 *@author panwei
 *@version 1.0
 *@since 2009/06/22
 */
public class ComplexComponentMeta extends SimpleComponentMeta implements
		ComponentMetaInterface {

	private static Class<?> PKG = ComplexComponentMeta.class;

	/**
	 * 多根对象
	 */
	private List<ComponentMetaInterface> content;

	/**
	 * 多根时返回
	 * 
	 * @return
	 */
	public boolean isMultiRoot() {
		return this.content != null && this.content.size() > 1;
	}

	public <T extends ComponentMetaInterface> void addContent(T c) {
		if (this.content == null) {
			this.content = new ArrayList<ComponentMetaInterface>();
		}
		this.content.add(c);
	}

	public <T extends ComponentMetaInterface> void addContent(int index, T c) {
		if (this.content == null) {
			this.content = new ArrayList<ComponentMetaInterface>();
		}
		this.content.add(index, c);
	}

	public <T extends ComponentMetaInterface> void addContentFirse(T c) {
		if (this.content == null) {
			this.content = new ArrayList<ComponentMetaInterface>();
		}
		this.content.add(0, c);
	}

	public void addContents(List<? extends ComponentMetaInterface> c) {
		if (this.content == null) {
			this.content = new ArrayList<ComponentMetaInterface>();
		}
		this.content.addAll(c);
	}

	public List<? extends ComponentMetaInterface> getContents() {
		return this.content;
	}

	/**
	 * 得到单根JSON对象
	 * 
	 * @return
	 * @throws ImetaFormException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormJo() throws BIJSONException {
		JSONObject formJo = super.getFormJo();
		if (this.content != null && this.content.size() > 0) {
			JSONArray sub = new JSONArray();
			for (ComponentMetaInterface dataMeta : this.content) {
				if (dataMeta != null) {
					if (dataMeta instanceof ComplexComponentMeta) {
						if (((ComplexComponentMeta) dataMeta).isMultiRoot()) {
							sub.addAll(((ComplexComponentMeta) dataMeta)
									.getFormJa());
						} else {
							sub.add(dataMeta.getFormJo());
						}
					} else {
						sub.add(dataMeta.getFormJo());
					}
				}
			}
			formJo.put(HTML.TAG_CONTENT, sub);
		}
		return formJo;
	}

	/**
	 * 得到多根JSON数组对象
	 * 
	 * @return
	 * @throws ImetaFormException
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getFormJa() throws BIJSONException {
		if (this.content != null && this.content.size() > 0) {
			JSONArray rtn = new JSONArray();
			for (ComponentMetaInterface meta : this.content) {
				rtn.add(meta.getFormJo());
			}
			return rtn;
		}
		return null;
	}

	@Override
	public void append(ComponentMetaInterface content) {
		if (this.content == null) {
			this.content = new ArrayList<ComponentMetaInterface>();
		}
		this.content.add(content);
	}

	@Override
	public String toString() {
		try {
			if (this.isMultiRoot()) {
				return getFormJa().toString();
			} else {
				return getFormJo().toString();
			}
		} catch (BIJSONException e) {
			return BaseMessages.getString(PKG, "IForm.CreateJSON.Error");
		}
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_COMPLEX;
	}

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public void removeAll() {
		this.content = null;
		super.removeAll();
	}
}
