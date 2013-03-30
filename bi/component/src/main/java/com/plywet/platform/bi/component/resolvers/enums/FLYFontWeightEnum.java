package com.plywet.platform.bi.component.resolvers.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.plywet.platform.bi.component.core.ComponentEnumInterface;

public enum FLYFontWeightEnum implements ComponentEnumInterface {
	normal, bold, bolder, lighter;

	public static final String COMPONENT_ENUM_NAME = "FontWeight";

	private static Class<?> PKG = FLYFontWeightEnum.class;

	@Override
	public String getText() {
		return BaseMessages.getString(PKG, COMPONENT_ENUM_LONG_DESC_PREFIX
				+ "." + COMPONENT_ENUM_NAME + "." + this.name());
	}

	@Override
	public String getValue() {
		return this.name();
	}

}
