package com.plywet.platform.lic;

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
		if (this.expiredDate == null) {
			this.expiredDate = new Date();
		}

		return Base64.encodeObject(StringUtil
				.getFormattedDateTime(this.expiredDate));
	}

	public LicenseObject setConcurrent(int concurrent) {
		this.concurrent = concurrent;
		return this;
	}

	private String getConcurrentBase64String() {
		return Base64.encodeObject(String.valueOf(this.concurrent));
	}

	public String getLicenseText(String userMessage) throws BILicenseException {
		if (this.licenseType == null) {
			throw new BILicenseException("Lic.Message.No.Model");
		}
		String text = this.licenseType.getLicenseSignText(userMessage);
		text = text + ",";
		text = text + this.licenseType.getIdBase64();
		text = text + ",";
		text = text + getExpiredDateBase64String();
		text = text + ",";
		text = text + getConcurrentBase64String();

		return text;
	}

}
