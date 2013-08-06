package com.flywet.platform.bi.report.enums;

import org.pentaho.di.i18n.BaseMessages;

/**
 * 垂直对齐枚举类
 * 
 * @author PeterPan
 * 
 */
public enum VerticalEnum implements ISpreadSheetEnum {
	// 向上对齐
	VERTICAL_TOP((short) 0),
	// 居中对齐
	VERTICAL_CENTER((short) 1),
	// 向下对齐
	VERTICAL_BOTTOM((short) 2);

	public static final String ENUM_NAME = "Vertical";

	private static Class<?> PKG = VerticalEnum.class;

	VerticalEnum(short index) {
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
