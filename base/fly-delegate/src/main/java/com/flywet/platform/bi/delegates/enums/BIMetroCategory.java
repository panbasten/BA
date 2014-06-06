package com.flywet.platform.bi.delegates.enums;

import org.pentaho.di.i18n.BaseMessages;

public enum BIMetroCategory {
	// Metro（默认）
	METRO_TYPE_METRO(0, "metro", "Metro.Category.Metro.Description"),
	// 记事本
	METRO_TYPE_NOTE(1, "note", "Metro.Category.Note.Description"),
	// 图片集
	METRO_TYPE_CYCLE(2, "cycle", "Metro.Category.Cycle.Description"),
	// 报表
	METRO_TYPE_REPORT(3, "report", "Metro.Category.Report.Description"),
	// 自定义
	METRO_TYPE_CUSTOM(9, "custom", "Metro.Category.Custom.Description");

	private static Class<?> PKG = BIMetroCategory.class;

	private int id;
	private String category;
	private String desc;

	private BIMetroCategory(int id, String category, String desc) {
		this.id = id;
		this.category = category;
		this.desc = desc;
	}

	public String getCategory() {
		return category;
	}

	public String getDesc() {
		return BaseMessages.getString(PKG, desc);
	}

	public int getId() {
		return id;
	}

	public static BIMetroCategory getCategoryById(int id) {
		for (BIMetroCategory fsc : values()) {
			if (fsc.getId() == id) {
				return fsc;
			}
		}
		return null;
	}

	public static BIMetroCategory getCategoryByCode(String category) {
		for (BIMetroCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc;
			}
		}
		return null;
	}

	public static String BIMetroCategory(String category) {
		for (BIMetroCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc.getDesc();
			}
		}
		return null;
	}

}
