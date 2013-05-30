package com.plywet.platform.bi.core.sec;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.version.BuildVersion;

import com.plywet.platform.bi.core.exception.BISecurityException;
import com.plywet.platform.bi.core.utils.FileUtils;

public class WebMarshal {

	private static Class<?> PKG = WebMarshal.class;

	public static final String OS_NAME = "os.name";
	public static final String OS_ARCH = "os.arch";
	public static final String OS_VER = "os.version";

	public static final String LIC_FILE_NAME = "ba.lic";

	private String os_name;
	private String os_arch;
	private String os_version;

	private String version;
	private String revision;
	private String build_date;
	private String build_user;

	private String mac_address;

	private String customerFullName;

	private List<String[]> authModules = new ArrayList<String[]>();

	private static WebMarshal webMarshal;

	public static final WebMarshal getInstance() throws BISecurityException {
		if (webMarshal != null)
			return webMarshal;
		webMarshal = new WebMarshal();

		return webMarshal;
	}

	private WebMarshal() throws BISecurityException {
		try {
			initOsProperty();
			initBuildVersion();
			initMacAddress();
			checkLicense();
		} catch (BISecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new BISecurityException(BaseMessages.getString(PKG,
					"Lic.Message.Cann.Get.Machine.Code"));
		}
	}

	private final void checkLicense() throws BISecurityException {
		String lic = getLicenseFileString();
		try {
			List<String[]> licList = SignProvider.decodeLicense(lic);
			if (licList == null) {
				throw new BISecurityException(BaseMessages.getString(PKG,
						"Lic.Message.Invalid.License"));
			}

			this.customerFullName = licList.get(0)[0];

			if (!this.mac_address.equalsIgnoreCase(licList.get(0)[1])) {
				throw new BISecurityException(BaseMessages.getString(PKG,
						"Lic.Message.Invalid.License"));
			}

			this.authModules.clear();
			for (int i = 1; i < licList.size(); i++) {
				this.authModules.add(licList.get(i));
			}

		} catch (BISecurityException e) {
			throw e;
		} catch (Exception e) {
			throw new BISecurityException(BaseMessages.getString(PKG,
					"Lic.Message.Load.License.Fail"));
		}

	}

	private final void initMacAddress() throws BISecurityException {

		try {
			this.mac_address = Const.getMACAddress();
		} catch (Exception e) {
			throw new BISecurityException(BaseMessages.getString(PKG,
					"Lic.Message.Cann.Get.Machine.Code"));
		}

		if (this.mac_address == null) {
			throw new BISecurityException(BaseMessages.getString(PKG,
					"Lic.Message.Cann.Get.Machine.Code"));
		}
	}

	private final void initOsProperty() {
		this.os_name = System.getProperty(OS_NAME);
		this.os_arch = System.getProperty(OS_ARCH);
		this.os_version = System.getProperty(OS_VER);
	}

	private final void initBuildVersion() {
		this.version = BuildVersion.getInstance().getVersion();
		this.revision = BuildVersion.getInstance().getRevision();
		this.build_date = BuildVersion.getInstance().getBuildDate();
		this.build_user = BuildVersion.getInstance().getBuildUser();
	}

	/**
	 * 获得License文件内容
	 * 
	 * @throws BISecurityException
	 */
	private final String getLicenseFileString() throws BISecurityException {
		String licFilePath = Const.getKettleDirectory() + Const.FILE_SEPARATOR
				+ LIC_FILE_NAME;
		if (!FileUtils.isFileExist(licFilePath)) {
			throw new BISecurityException(BaseMessages.getString(PKG,
					"Lic.Message.No.License"));
		}

		try {
			InputStream is = new FileInputStream(licFilePath);
			return FileUtils.getString(is);
		} catch (Exception e) {
			throw new BISecurityException(BaseMessages.getString(PKG,
					"Lic.Message.No.License"));
		}

	}

	public String getOsName() {
		return os_name;
	}

	public String getOsArch() {
		return os_arch;
	}

	public String getOsVersion() {
		return os_version;
	}

	public String getVersion() {
		return version;
	}

	public String getRevision() {
		return revision;
	}

	public String getBuildDate() {
		return build_date;
	}

	public String getBuildUser() {
		return build_user;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public List<String[]> getAuthModules() {
		return authModules;
	}

}
