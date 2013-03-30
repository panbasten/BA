package com.plywet.platform.bi.component.resolvers.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.plywet.platform.bi.component.core.ComponentEnumInterface;

public enum FLYColorEnum implements ComponentEnumInterface {

	aqua, black, blue, fuchsia, gray, green, lime, maroon, navy, olive, purple, red, silver, teal, white, yellow;

	public static final String COMPONENT_ENUM_NAME = "Color";

	private static Class<?> PKG = FLYColorEnum.class;

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
