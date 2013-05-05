package com.plywet.platform.bi.delegates.utils;

public enum BIReportCategory {
	REPORT_TYPE_FORM(1, "dashboard", "仪表板") {
	},
	REPORT_TYPE_WORD_REPORT(2, "wordreport", "报告") {
	},
	REPORT_TYPE_FREE_REPORT(3, "freereport", "自由报表") {
	},
	REPORT_TYPE_PIVOT_REPORT(4, "pivotreport", "透视报表") {
	};

	private int id;
	private String category;
	private String desc;

	private BIReportCategory(int id, String category, String desc) {
		this.id = id;
		this.category = category;
		this.desc = desc;
	}

	public static BIReportCategory getCategoryById(int id) {
		for (BIReportCategory fsc : values()) {
			if (fsc.getId() == id) {
				return fsc;
			}
		}
		return null;
	}

	public static BIReportCategory getCategoryByCode(String category) {
		for (BIReportCategory fsc : values()) {
			if (fsc.getCategory().equals(category)) {
				return fsc;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
