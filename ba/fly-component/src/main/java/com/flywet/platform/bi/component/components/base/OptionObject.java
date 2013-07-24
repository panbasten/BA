package com.flywet.platform.bi.component.components.base;

import org.pentaho.pms.util.Const;

/**
 * 选取框数据管理功能
 * 
 * @author panwei
 * @version 1.0
 * @since 2009/06/22
 */
public class OptionObject implements Cloneable {
	/**
	 * 值
	 */
	private String value = null;

	/**
	 * 文字
	 */
	private String text = null;

	/**
	 * 是否选定
	 */
	private boolean selected = false;

	public OptionObject() {
		this("", "");
	}

	/**
	 * 选取框数据管理
	 * 
	 * @param value
	 *            初始值
	 * @param text
	 *            文本
	 */
	public OptionObject(String value, String text) {
		this.value = Const.NVL(value, "");
		this.text = Const.NVL(text, "");
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
