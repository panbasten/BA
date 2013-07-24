package com.flywet.platform.lic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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

	public static final String TRIAL_VERSION = "-TRIAL-";

	public static final String BASE_VERSION = "-BASE-";

	public static final String OFFICIAL_VERSION = "-OFFICIAL-";

	public static final String UNREGISTERED_CUSTOMER = "-UNREGISTERED-";

	public static final int TRIAL_EXPIRED_DATE = 90;

	/**
	 * 用户全称
	 */
	private String customerFullName;

	/**
	 * 版本
	 */
	private String version = OFFICIAL_VERSION;

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

	public void addLicenses(int[] ids) {
		for (int id : ids) {
			addLicense(id);
		}
	}

	public LicenseObject addLicense(int id, int expiredDate) {
		LicenseObject lo = LicenseObject.instance().setExpiredDate(expiredDate)
				.setLicenseType(LicenseEnums.getLicenseEnumById(id));
		this.licenses.put(id, lo);
		return lo;
	}

	public void addLicenses(int[] ids, int expiredDate) {
		for (int id : ids) {
			addLicense(id, expiredDate);
		}
	}

	public void addModuleLicenses(String module) {
		for (LicenseEnums le : LicenseEnums.values()) {
			if (le.getCode().startsWith(module)) {
				this.addLicense(le.getId());
			}
		}
	}

	public LicenseObject addLicense(int id, int expiredDate, int concurrent) {
		LicenseObject lo = addLicense(id, expiredDate)
				.setConcurrent(concurrent);
		this.licenses.put(id, lo);
		return lo;
	}

	public void addLicenses(int[] ids, int expiredDate, int concurrent) {
		for (int id : ids) {
			addLicense(id, expiredDate, concurrent);
		}
	}

	public void addBaseLicenses() {
		for (LicenseEnums le : LicenseEnums.values()) {
			if (le.getCode().contains("Base")) {
				this.addLicense(le.getId());
			}
		}
	}

	private String getUserMessage() {
		String text = Base64.encodeObject(this.customerFullName);
		text = text + ",";
		text = text + Base64.encodeObject(this.macAddress);
		text = text + ",";
		text = text + Base64.encodeObject(this.version);
		return text;
	}

	public void setTrialVersion() {
		this.customerFullName = UNREGISTERED_CUSTOMER;
		this.version = TRIAL_VERSION;
		this.macAddress = MARK_ALL_MAC;
		for (LicenseEnums le : LicenseEnums.values()) {
			this.addLicense(le.getId(), TRIAL_EXPIRED_DATE);
		}
	}

	public void setBaseVersion() {
		this.customerFullName = UNREGISTERED_CUSTOMER;
		this.version = BASE_VERSION;
		this.macAddress = MARK_ALL_MAC;

		this.addBaseLicenses();
	}

	public String getLicenseText() throws BILicenseException {
		if (StringUtils.isEmpty(this.customerFullName)) {
			throw new BILicenseException("Lic.Message.Cannot.Generate.License");
		}
		if (StringUtils.isEmpty(this.macAddress)) {
			throw new BILicenseException("Lic.Message.Cannot.Generate.License");
		}
		String userMessage = this.customerFullName + this.macAddress
				+ this.version;

		StringBuffer text = new StringBuffer();
		text.append(getUserMessage());

		for (LicenseObject lo : this.licenses.values()) {
			text.append(",,");
			text.append(lo.getLicenseText(userMessage));
		}

		// 采用zip压缩获得字节码
		byte[] zip = zipCompress(text.toString());

		// 使用BASE64转string
		return Base64.encodeBytes(zip);
	}

	/**
	 * 压缩字符串为 byte[] 保存为字符串
	 * 
	 * @param str
	 *            压缩前的文本
	 * @return
	 */
	private byte[] zipCompress(String str) {
		if (str == null)
			return null;

		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return compressed;
	}

	/**
	 * 将压缩后的 byte[] 数据解压缩
	 * 
	 * @param compressed
	 *            压缩后的 byte[] 数据
	 * @return 解压后的字符串
	 */
	public String zipDecompress(byte[] compressed) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return decompressed;
	}

}
