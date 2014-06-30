package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.pivot.model.IIndexedEnum;

/**
 * 字形枚举类
 * 
 * @author PeterPan
 * 
 */
public enum FontStyleEnum implements IIndexedEnum {
	// 正常
	FONT_STYLE_NORMAL((short) 0),
	// 倾斜
	FONT_STYLE_ITALIC((short) 1);

	public static final String ENUM_NAME = "FontStyle";

	private static Class<?> PKG = FontStyleEnum.class;

	FontStyleEnum(short index) {
		this.index = index;
	}

	private short index;

	public static FontStyleEnum get(short index) {
		for (FontStyleEnum e : FontStyleEnum.values()) {
			if (e.index == index) {
				return e;
			}
		}
		return null;
	}

	@Override
	public short getIndex() {
		return index;
	}

	@Override
	public String getText() {
		return BaseMessages.getString(PKG, ENUM_LONG_DESC_PREFIX + "."
				+ ENUM_NAME + "." + this.name());
	}
}
