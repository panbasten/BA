package com.flywet.platform.bi.report.enums;

import org.pentaho.di.i18n.BaseMessages;

/**
 * 字形枚举类
 * 
 * @author PeterPan
 * 
 */
public enum FontStyleEnum implements ISpreadSheetEnum {
	// 常规
	FONT_STYLE_GENERAL((short) 0),
	// 倾斜
	FONT_STYLE_ITALIC((short) 1),
	// 加粗
	FONT_STYLE_BOLD((short) 2),
	// 加粗倾斜
	FONT_STYLE_BOLD_ITALIC((short) 3);

	public static final String ENUM_NAME = "FontStyle";

	private static Class<?> PKG = FontStyleEnum.class;

	FontStyleEnum(short index) {
		this.index = index;
	}

	private short index;

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
