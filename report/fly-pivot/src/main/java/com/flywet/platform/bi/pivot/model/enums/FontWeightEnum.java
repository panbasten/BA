package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.pivot.model.IIndexedEnum;

public enum FontWeightEnum implements IIndexedEnum {

	// 正常
	FONT_WEIGHT_NORMAL((short) 0),
	// 变细
	FONT_WEIGHT_LIGHTER((short) 1),
	// 变粗
	FONT_WEIGHT_BOLDER((short) 2),
	// 粗
	FONT_WEIGHT_BOLD((short) 3);

	public static final String ENUM_NAME = "FontWeight";

	private static Class<?> PKG = FontWeightEnum.class;

	FontWeightEnum(short index) {
		this.index = index;
	}

	private short index;

	public static FontWeightEnum get(short index) {
		for (FontWeightEnum e : FontWeightEnum.values()) {
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
