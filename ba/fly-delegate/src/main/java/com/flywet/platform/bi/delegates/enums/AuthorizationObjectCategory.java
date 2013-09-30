package com.flywet.platform.bi.delegates.enums;

import org.pentaho.di.i18n.BaseMessages;

public enum AuthorizationObjectCategory {
	FUNCTION(1, "func", "AuthorizationObject.Category.Function.Description") {
	},
	PORTAL_MENU(2, "portal",
			"AuthorizationObject.Category.PortalMenu.Description") {
	},
	BUTTON(3, "btn", "AuthorizationObject.Category.Button.Description") {
	},
	CUSTOM(4, "cust", "AuthorizationObject.Category.Custom.Description");

	private static Class<?> PKG = AuthorizationObjectCategory.class;

	private int id;
	private String category;
	private String desc;

	private AuthorizationObjectCategory(int id, String category, String desc) {
		this.id = id;
		this.category = category;
		this.desc = desc;
	}

	public static AuthorizationObjectCategory getCategoryById(int id) {
		for (AuthorizationObjectCategory fsc : values()) {
			if (fsc.getId() == id) {
				return fsc;
			}
		}
		return null;
	}

	public static AuthorizationObjectCategory getCategoryByCode(String category) {
		for (AuthorizationObjectCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public String getDesc() {
		return BaseMessages.getString(PKG, desc);
	}
}
