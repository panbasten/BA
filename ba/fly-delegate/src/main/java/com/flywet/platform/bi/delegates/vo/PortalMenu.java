package com.flywet.platform.bi.delegates.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.flywet.platform.bi.core.model.NameValuePair;
import com.flywet.platform.bi.core.utils.Utils;

public class PortalMenu {
	private long id;
	private String code;
	private String moduleCode;
	private String desc;
	private long parentId;
	private String helpText;
	private int index;

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

	public String getExtAttrString() {
		if (Utils.isEmpty(extAttrs)) {
			return StringUtils.EMPTY;
		}
		String str = "";
		str = "{";
		for (NameValuePair pair : extAttrs) {
			str += pair.getName() + ":'" + pair.getValue() + "',";
		}
		str = StringUtils.removeEnd(str, ",");
		str += "}";
		return str;
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
