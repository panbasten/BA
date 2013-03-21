package com.plywet.platform.bi.component.vo;

public class BaseComponentAttribute {
	protected String name;

	protected String description;
	protected String tooltip;

	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}
