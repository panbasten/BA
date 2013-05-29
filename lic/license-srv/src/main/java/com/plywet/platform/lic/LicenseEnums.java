package com.plywet.platform.lic;

import org.pentaho.di.i18n.BaseMessages;

public enum LicenseEnums {
	DB_BASE(101, "DB.Base", LicenseEnums.LIC_CATEGORY_DB) {
	},
	DI_BASE(201, "DI.Base", LicenseEnums.LIC_CATEGORY_DI) {
	},
	DI_TRANS_EDITOR(202, "DI.Trans.Editor", LicenseEnums.LIC_CATEGORY_DI) {
	},
	DI_JOB_EDITOR(203, "DI.Job.Editor", LicenseEnums.LIC_CATEGORY_DI) {
	},
	BA_BASE(301, "BA.Base", LicenseEnums.LIC_CATEGORY_BA) {
	},
	BA_REPORT_EDITOR(302, "BA.Report.Editor", LicenseEnums.LIC_CATEGORY_BA) {
	},
	BA_PIVOT_REPORT_EDITOR(303, "BA.PivotReport.Editor",
			LicenseEnums.LIC_CATEGORY_BA) {
	},
	BA_WORD_REPORT_EDITOR(304, "BA.WordReport.Editor",
			LicenseEnums.LIC_CATEGORY_BA) {
	},
	BA_DASHBOARD_EDITOR(305, "BA.DashBoard.Editor",
			LicenseEnums.LIC_CATEGORY_BA) {
	},
	FS_BASE(401, "FS.Base", LicenseEnums.LIC_CATEGORY_FS) {
	},
	FS_LOCAL_EDITOR(402, "FS.Local.Editor", LicenseEnums.LIC_CATEGORY_FS) {
	},
	FS_FTP_EDITOR(403, "FS.FTP.Editor", LicenseEnums.LIC_CATEGORY_FS) {
	};

	private static final String KEY = "PLYWET@2013";

	public static final String LIC_CATEGORY_DB = "DB";
	public static final String LIC_CATEGORY_DI = "DI";
	public static final String LIC_CATEGORY_BA = "BA";
	public static final String LIC_CATEGORY_FS = "FS";

	private static final KeyGenerater kg = KeyGenerater.instance(KEY);

	private int id;
	private String code;
	private String category;
	private String description;
	private String helpText;

	LicenseEnums(int id, String code, String category) {
		this.id = id;
		this.code = code;
		this.category = category;
		this.description = BaseMessages.getString(LicenseEnums.class,
				"Lic.Model." + this.code + ".Description");
		this.helpText = BaseMessages.getString(LicenseEnums.class, "Lic.Model."
				+ this.code + ".HelpText");
	}

	public String getLicenseSignText(String userMessage) {
		return Signaturer.sign(kg.getPriKey(), userMessage + this.code);
	}

	public int getId() {
		return id;
	}

	public String getIdBase64() {
		return Base64.encodeObject(Integer.valueOf(this.id));
	}

	public String getCode() {
		return code;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public String getHelpText() {
		return helpText;
	}

	public static LicenseEnums getLicenseEnumById(int id) {
		for (LicenseEnums en : LicenseEnums.values()) {
			if (en.getId() == id) {
				return en;
			}
		}
		return null;
	}

}
