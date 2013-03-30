package com.plywet.platform.bi.component.resolvers.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.plywet.platform.bi.component.core.ComponentEnumInterface;

public enum FLYBorderStyleEnum implements ComponentEnumInterface {

	none, dotted, dashed, solid, _double {
		@Override
		public String getValue() {
			return "double";
		}
	},
	groove, ridge, inset, outset;

	public static final String COMPONENT_ENUM_NAME = "BorderStyle";

	private static Class<?> PKG = FLYBorderStyleEnum.class;

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
