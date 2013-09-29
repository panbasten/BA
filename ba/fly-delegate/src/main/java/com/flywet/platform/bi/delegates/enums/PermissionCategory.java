package com.flywet.platform.bi.delegates.enums;

import org.pentaho.di.i18n.BaseMessages;

public enum PermissionCategory {
	R(4, "r", "Permission.Category.Read.Description") {
	},
	W(2, "w", "Permission.Category.Write.Description") {
	},
	X(1, "x", "Permission.Category.Execute.Description") {
	};

	private static Class<?> PKG = PermissionCategory.class;

	private int id;
	private String category;
	private String desc;

	private PermissionCategory(int id, String category, String desc) {
		this.id = id;
		this.category = category;
		this.desc = desc;
	}

	public static PermissionCategory getCategoryById(int id) {
		for (PermissionCategory fsc : values()) {
			if (fsc.getId() == id) {
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
