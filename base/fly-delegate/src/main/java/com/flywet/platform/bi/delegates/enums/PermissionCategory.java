package com.flywet.platform.bi.delegates.enums;

import org.pentaho.di.i18n.BaseMessages;

public enum PermissionCategory {
	ALL(7, "all", "Permission.Category.All.Description") {
	},
	R(4, "r", "Permission.Category.Read.Description") {
	},
	W(2, "w", "Permission.Category.Write.Description") {
	},
	X(1, "x", "Permission.Category.Execute.Description") {
	};

	private static Class<?> PKG = PermissionCategory.class;

	private int value;
	private String category;
	private String desc;

	private PermissionCategory(int value, String category, String desc) {
		this.value = value;
		this.category = category;
		this.desc = desc;
	}

	public static PermissionCategory getCategoryById(int id) {
		for (PermissionCategory fsc : values()) {
			if (fsc.getValue() == id) {
				return fsc;
			}
		}
		return null;
	}

	public static PermissionCategory getCategoryByCode(String category) {
		for (PermissionCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc;
			}
		}
		return null;
	}

	public int getValue() {
		return value;
	}

	public String getCategory() {
		return category;
	}

	public String getDesc() {
		return BaseMessages.getString(PKG, desc);
	}

}
