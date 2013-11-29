package com.flywet.platform.bi.component.ss.model;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentObjectInterface;

public class Column implements ComponentObjectInterface {

	private static final long serialVersionUID = -3565268295305190586L;

	private Sheet parent;

	// 宽度
	private Integer width;

	// 样式索引
	private Integer styleIdx;

	// 是否隐藏
	private boolean hidden = false;

	public Column(Sheet parent) {
		this.parent = parent;
	}

	public boolean isSetWidth() {
		return (width != null);
	}

	public Integer getWidth() {
		return width;
	}

	public boolean isSetStyle() {
		return (styleIdx != null);
	}

	public Integer getStyleIndex() {
		return styleIdx;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
