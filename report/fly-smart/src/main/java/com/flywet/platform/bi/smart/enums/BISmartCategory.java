package com.flywet.platform.bi.smart.enums;

import org.pentaho.di.i18n.BaseMessages;

public enum BISmartCategory {
	SMART_TYPE_OLAP(1, "olap", "Smart.Category.OLAP.Description");

	private static Class<?> PKG = BISmartCategory.class;

	private int id;
	private String category;
	private String desc;

	private BISmartCategory(int id, String category, String desc) {
		this.id = id;
		this.category = category;
		this.desc = desc;
	}

	public static BISmartCategory getCategoryById(int id) {
		for (BISmartCategory sc : values()) {
			if (sc.getId() == id) {
				return sc;
			}
		}
		return null;
	}

	public static BISmartCategory getCategoryByCode(int category) {
		for (BISmartCategory sc : values()) {
			if (sc.getCategory().equals(category)) {
				return sc;
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
