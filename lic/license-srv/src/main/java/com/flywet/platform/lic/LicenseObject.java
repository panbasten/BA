package com.flywet.platform.lic;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.util.StringUtil;

public class LicenseObject {
	private LicenseEnums licenseType;
	private Date expiredDate;
	private int concurrent = -1;

	public static LicenseObject instance() {
		return new LicenseObject();
	}

	public LicenseObject setLicenseType(LicenseEnums licenseType) {
		this.licenseType = licenseType;
		return this;
	}

	public LicenseObject setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
		return this;
	}

	public LicenseObject setExpiredDate(int day) {
		this.expiredDate = new Date();
		this.expiredDate = DateUtils.addDays(this.expiredDate, day);
		return this;
	}

	public LicenseObject setMaxExpiredDate() {
		this.expiredDate = Const.MAX_DATE;
		return this;
	}

	private String getExpiredDateBase64String() {
		return Base64.encodeObject(getExpiredDateString());
	}

	public String getExpiredDateString() {
		if (this.expiredDate == null) {
			this.expiredDate = new Date();
		}
		return StringUtil.getFormattedDateTime(this.expiredDate);
	}

	public LicenseObject setConcurrent(int concurrent) {
		this.concurrent = concurrent;
		return this;
	}

	private String getConcurrentBase64String() {
		return Base64.encodeObject(getConcurrentString());
	}

	public String getConcurrentString() {
		return String.valueOf(this.concurrent);
	}

	public String getLicenseText(String userMessage) throws BILicenseException {
		if (this.licenseType == null) {
			throw new BILicenseException("Lic.Message.No.Module");
		}
		String text = this.licenseType.getLicenseSignText(userMessage, this);
		text = text + ",";
		text = text + this.licenseType.getIdBase64();
		text = text + ",";
		text = text + this.licenseType.getCodeBase64();
		text = text + ",";
		text = text + getExpiredDateBase64String();
		text = text + ",";
		text = text + getConcurrentBase64String();

		return text;
	}

}
