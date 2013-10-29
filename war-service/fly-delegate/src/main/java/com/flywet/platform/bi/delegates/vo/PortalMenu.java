package com.flywet.platform.bi.delegates.vo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.model.NameValuePair;
import com.flywet.platform.bi.core.utils.JSONUtils;

public class PortalMenu {
	private long id;
	private String code;
	private String moduleCode;
	private String desc;
	private long parentId;
	private String helpText;
	private int index;
	private boolean authenticate;

	private List<PortalMenu> children = new ArrayList<PortalMenu>();
	private List<NameValuePair> extAttrs = new ArrayList<NameValuePair>();

	public void addChild(PortalMenu child) {
		children.add(child);
	}

	public void addExtAttr(NameValuePair attr) {
		extAttrs.add(attr);
	}

	public String getExtAttr(String name) {
		for (NameValuePair nvp : extAttrs) {
			if (nvp.getName().equals(name)) {
				return nvp.getValue();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getSimpleJSON() throws BIJSONException {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("code", code);
		jo.put("moduleCode", moduleCode);
		jo.put("desc", desc);
		jo.put("parentId", parentId);
		jo.put("helpText", helpText);
		jo.put("index", index);
		jo.put("extAttrs", JSONUtils.convertToJSONArray(extAttrs));
		return jo;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getJSON() throws BIJSONException {
		JSONObject jo = getSimpleJSON();
		jo.put("children", JSONUtils.convertToJSONArray(children));
		return jo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isAuthenticate() {
		return authenticate;
	}

	public void setAuthenticate(boolean authenticate) {
		this.authenticate = authenticate;
	}

	public List<PortalMenu> getChildren() {
		return children;
	}

	public void setChildren(List<PortalMenu> children) {
		this.children = children;
	}

	public List<NameValuePair> getExtAttrs() {
		return extAttrs;
	}

	public void setExtAttrs(List<NameValuePair> extAttrs) {
		this.extAttrs = extAttrs;
	}
}
