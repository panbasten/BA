package com.plywet.platform.bi.component.vo;


public class BaseComponentAttribute {
	protected String name;

	protected String description;
	protected String tooltip;

	protected String editorType;
	protected String editorOptionEnum;

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

	public String getEditorType() {
		return editorType;
	}

	public void setEditorType(String editorType) {
		this.editorType = editorType;
	}

	public String getEditorOptionEnum() {
		return editorOptionEnum;
	}

	public void setEditorOptionEnum(String editorOptionEnum) {
		this.editorOptionEnum = editorOptionEnum;
	}

}
