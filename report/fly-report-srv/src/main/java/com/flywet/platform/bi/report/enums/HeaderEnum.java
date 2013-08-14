package com.flywet.platform.bi.report.enums;

import org.pentaho.di.i18n.BaseMessages;

public enum HeaderEnum implements ISpreadSheetEnum {

	// 行
	HEADER_ROW((short) 0),
	// 列
	HEADER_COLUMN((short) 1);

	public static final String ENUM_NAME = "Header";

	private static Class<?> PKG = HeaderEnum.class;

	HeaderEnum(short index) {
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
