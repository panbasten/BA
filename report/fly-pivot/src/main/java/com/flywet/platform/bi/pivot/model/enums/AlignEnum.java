package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.pivot.model.IIndexedEnum;

/**
 * 水平对齐枚举类
 * 
 * @author PeterPan
 * 
 */
public enum AlignEnum implements IIndexedEnum {
	// 居左对齐
	ALIGN_LEFT((short) 0),
	// 居中对齐
	ALIGN_CENTER((short) 1),
	// 居右对齐
	ALIGN_RIGHT((short) 2),
	// 两边对齐
	ALIGN_JUSTIFY((short) 3);

	public static final String ENUM_NAME = "Align";

	private static Class<?> PKG = AlignEnum.class;

	AlignEnum(short index) {
		this.index = index;
	}

	private short index;

	public static AlignEnum get(short index) {
		for (AlignEnum e : AlignEnum.values()) {
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
