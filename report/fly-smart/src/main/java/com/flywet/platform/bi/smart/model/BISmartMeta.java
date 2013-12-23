package com.flywet.platform.bi.smart.model;

import com.flywet.platform.bi.smart.enums.BISmartCategory;

public class BISmartMeta {

	private Long idSmart;
	private Long idSmartDirectory;
	private Long idDatabase;
	private String smartObject;
	private BISmartCategory smartType;
	private String description;
	private String smartVersion;

	public BISmartMeta(Object[] obj) {
		if (obj == null) {
			return;
		}
		if (obj[0] != null) {
			this.idSmart = (Long) obj[0];
		}
		if (obj[1] != null) {
			this.idSmartDirectory = (Long) obj[1];
		}
		if (obj[2] != null) {
			this.idDatabase = (Long) obj[2];
		}
		if (obj[3] != null) {
			this.smartObject = String.valueOf(obj[3]);
		}
		if (obj[4] != null) {
			this.smartType = BISmartCategory.getCategoryById(((Long) obj[4])
					.intValue());
		}
		if (obj[5] != null) {
			this.description = String.valueOf(obj[5]);
		}
		if (obj[6] != null) {
			this.description = String.valueOf(obj[6]);
		}
	}

	public Long getIdSmart() {
		return idSmart;
	}

	public Long getIdSmartDirectory() {
		return idSmartDirectory;
	}

	public Long getIdDatabase() {
		return idDatabase;
	}

	public String getSmartObject() {
		return smartObject;
	}

	public BISmartCategory getSmartType() {
		return smartType;
	}

	public String getDescription() {
		return description;
	}

	public String getSmartVersion() {
		return smartVersion;
	}

}
