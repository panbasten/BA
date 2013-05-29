package com.plywet.platform.lic;

import java.util.HashMap;
import java.util.Map;

import org.apache.axis.utils.StringUtils;

/**
 * 对于License生成<br>
 * 绑定用户全称和Mac地址，如果Mac地址为-ALL-，则不限制
 * 
 * @author PeterPan
 * 
 */
public class LicenseGenerator {

	public static final String MARK_ALL_MAC = "-ALL-";

	/**
	 * 用户全称
	 */
	private String customerFullName;

	/**
	 * MAC地址
	 */
	private String macAddress;

	public static LicenseGenerator instance() {
		return new LicenseGenerator();
	}

	public LicenseGenerator setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
		return this;
	}

	public LicenseGenerator setMacAddress(String macAddress) {
		this.macAddress = macAddress;
		return this;
	}

	public LicenseGenerator setAllMacAddress() {
		this.macAddress = MARK_ALL_MAC;
		return this;
	}

	private Map<Integer, LicenseObject> licenses = new HashMap<Integer, LicenseObject>();

	public LicenseObject addLicense(int id) {
		LicenseObject lo = LicenseObject.instance().setMaxExpiredDate()
				.setLicenseType(LicenseEnums.getLicenseEnumById(id));
		this.licenses.put(id, lo);
		return lo;
	}

	public LicenseObject addLicense(int id, int expiredDate) {
		LicenseObject lo = LicenseObject.instance().setExpiredDate(expiredDate)
				.setLicenseType(LicenseEnums.getLicenseEnumById(id));
		this.licenses.put(id, lo);
		return lo;
	}

	public LicenseObject addLicense(int id, int expiredDate, int concurrent) {
		LicenseObject lo = addLicense(id, expiredDate)
				.setConcurrent(concurrent);
		this.licenses.put(id, lo);
		return lo;
	}

	private String getUserMessage() {
		String text = Base64.encodeObject(this.customerFullName);
		text = text + ",";
		text = Base64.encodeObject(this.macAddress);
		return text;
	}

	public String getLicenseText() throws BILicenseException {
		if (StringUtils.isEmpty(this.customerFullName)) {
			throw new BILicenseException("Lic.Message.Cannot.Generate.License");
		}
		if (StringUtils.isEmpty(this.macAddress)) {
			throw new BILicenseException("Lic.Message.Cannot.Generate.License");
		}
		String userMessage = this.customerFullName + this.macAddress;

		StringBuffer text = new StringBuffer();
		text.append(getUserMessage());

		for (LicenseObject lo : this.licenses.values()) {
			text.append(",,");
			text.append(lo.getLicenseText(userMessage));
		}

		return text.toString();
	}

}
