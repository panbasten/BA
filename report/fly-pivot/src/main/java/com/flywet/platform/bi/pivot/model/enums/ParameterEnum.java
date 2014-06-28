package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.pivot.model.IIndexedEnum;

/**
 * 参数类型枚举类
 * 
 * @author PeterPan
 * 
 */
public enum ParameterEnum implements IIndexedEnum {

	// 按钮组
	PARAMETER_BUTTON_GROUP((short) 0),
	// 字符串类型
	PARAMETER_STRING((short) 1),
	// 枚举类型
	PARAMETER_INT((short) 2);

	public static final String ENUM_NAME = "Parameter";

	private static Class<?> PKG = ParameterEnum.class;

	ParameterEnum(short index) {
		this.index = index;
	}

	private short index;

	public static ParameterEnum get(short index) {
		for (ParameterEnum e : ParameterEnum.values()) {
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
