package com.flywet.platform.bi.delegates.enums;

import org.pentaho.di.i18n.BaseMessages;

public enum BIReportCategory {
	REPORT_TYPE_DASHBOARD(1, "dashboard",
			"Report.Category.Dashboard.Description") {
	},
	REPORT_TYPE_WORD_REPORT(2, "wordreport",
			"Report.Category.WordReport.Description") {
	},
	REPORT_TYPE_REPORT(3, "report", "Report.Category.Report.Description") {
	},
	REPORT_TYPE_PIVOT_REPORT(4, "pivotreport",
			"Report.Category.PivotReport.Description") {
	};

	private static Class<?> PKG = BIReportCategory.class;

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

	public String getCategory() {
		return category;
	}

	public String getDesc() {
		return BaseMessages.getString(PKG, desc);
	}

}
