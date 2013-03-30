package com.plywet.platform.bi.component.vo;

import com.plywet.platform.bi.component.core.ComponentEnumInterface;

public class BaseComponentAttribute {
	protected String name;

	protected String description;
	protected String tooltip;

	protected String editorType;
	protected Class<? extends ComponentEnumInterface> editorOptionEnum;

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

	public Class<? extends ComponentEnumInterface> getEditorOptionEnum() {
		return editorOptionEnum;
	}

	public void setEditorOptionEnum(
			Class<? extends ComponentEnumInterface> editorOptionEnum) {
		this.editorOptionEnum = editorOptionEnum;
	}

}
