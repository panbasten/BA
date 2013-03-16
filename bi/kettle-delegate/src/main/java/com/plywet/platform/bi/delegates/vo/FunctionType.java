package com.plywet.platform.bi.delegates.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.plywet.platform.bi.core.model.NameValuePair;
import com.plywet.platform.bi.core.utils.Utils;

public class FunctionType {
	private long id;
	private String code;
	private String desc;
	private long parentId;
	private String helpText;
	private int index;

	private List<FunctionType> children = new ArrayList<FunctionType>();
	private List<NameValuePair> extAttrs = new ArrayList<NameValuePair>();

	public void addChild(FunctionType child) {
		children.add(child);
	}

	public void addExtAttr(NameValuePair attr) {
		extAttrs.add(attr);
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

	public List<FunctionType> getChildren() {
		return children;
	}

	public void setChildren(List<FunctionType> children) {
		this.children = children;
	}

	public List<NameValuePair> getExtAttrs() {
		return extAttrs;
	}

	public void setExtAttrs(List<NameValuePair> extAttrs) {
		this.extAttrs = extAttrs;
	}
}
