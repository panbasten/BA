package com.flywet.platform.bi.base.enums;

import org.pentaho.di.i18n.BaseMessages;

/**
 * 水平对齐枚举类
 * 
 * @author PeterPan
 * 
 */
public enum AlignEnum implements ISpreadSheetEnum {
	// 常规：缩进值无效
	ALIGN_GENERAL((short) 0),
	// 居左对齐
	ALIGN_LEFT((short) 1),
	// 居中对齐
	ALIGN_CENTER((short) 2),
	// 居右对齐
	ALIGN_RIGHT((short) 3);

	public static final String ENUM_NAME = "Align";

	private static Class<?> PKG = AlignEnum.class;

	AlignEnum(short index) {
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
