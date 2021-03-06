package com.flywet.platform.lic;

import org.pentaho.di.i18n.BaseMessages;

public enum LicenseEnums {
	SYS_BASE(1, "SYS.Base", LicenseEnums.LIC_CATEGORY_SYS) {
	},
	SYS_ADVANCE(2, "SYS.Advance", LicenseEnums.LIC_CATEGORY_SYS) {
	},
	DB_BASE(101, "DB.Base", LicenseEnums.LIC_CATEGORY_DB) {
	},
	DB_ADVANCE(102, "DB.Advance", LicenseEnums.LIC_CATEGORY_DB) {
	},
	SMART_BASE(201, "SM.Base", LicenseEnums.LIC_CATEGORY_SM) {
	},
	SMART_META(202, "SM.Meta", LicenseEnums.LIC_CATEGORY_SM) {
	},
	SMART_OLAP(203, "SM.Olap", LicenseEnums.LIC_CATEGORY_SM) {
	},
	DI_BASE(301, "DI.Base", LicenseEnums.LIC_CATEGORY_DI) {
	},
	DI_TRANS_EDITOR(302, "DI.Trans.Editor", LicenseEnums.LIC_CATEGORY_DI) {
	},
	DI_JOB_EDITOR(303, "DI.Job.Editor", LicenseEnums.LIC_CATEGORY_DI) {
	},
	BA_BASE(401, "BA.Base", LicenseEnums.LIC_CATEGORY_BA) {
	},
	BA_REPORT_EDITOR(402, "BA.Report.Editor", LicenseEnums.LIC_CATEGORY_BA) {
	},
	BA_OLAP_REPORT_EDITOR(403, "BA.OlapReport.Editor",
			LicenseEnums.LIC_CATEGORY_BA) {
	},
	BA_WORD_REPORT_EDITOR(404, "BA.WordReport.Editor",
			LicenseEnums.LIC_CATEGORY_BA) {
	},
	BA_DASHBOARD_EDITOR(405, "BA.DashBoard.Editor",
			LicenseEnums.LIC_CATEGORY_BA) {
	},
	FS_BASE(501, "FS.Base", LicenseEnums.LIC_CATEGORY_FS) {
	},
	FS_LOCAL(502, "FS.Local", LicenseEnums.LIC_CATEGORY_FS) {
	},
	FS_FTP(503, "FS.FTP", LicenseEnums.LIC_CATEGORY_FS) {
	};

	public static final String LIC_CATEGORY_SYS = "SYS";
	public static final String LIC_CATEGORY_DB = "DB";
	public static final String LIC_CATEGORY_SM = "SM";
	public static final String LIC_CATEGORY_DI = "DI";
	public static final String LIC_CATEGORY_BA = "BA";
	public static final String LIC_CATEGORY_FS = "FS";

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

	public String getLicenseSignText(String priKey, String userMessage,
			LicenseObject lo) {
		return Signaturer.sign(priKey, userMessage + this.code
				+ lo.getExpiredDateString() + lo.getConcurrentString());
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

	public String getCodeBase64() {
		return Base64.encodeObject(this.code);
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
