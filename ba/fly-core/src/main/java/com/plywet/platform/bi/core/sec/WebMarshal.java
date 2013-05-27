package com.plywet.platform.bi.core.sec;

import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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
		} catch (Exception e) {
			throw new BISecurityException(BaseMessages.getString(PKG,
					"Lic.Message.Cann.Get.Machine.Code"));
		}
	}

	private void initMacAddress() throws Exception {
		this.mac_address = Const.getMACAddress();
	}

	private void initOsProperty() {
		this.os_name = System.getProperty(OS_NAME);
		this.os_arch = System.getProperty(OS_ARCH);
		this.os_version = System.getProperty(OS_VER);
	}

	private void initBuildVersion() {
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
	private void getLicenseFile() throws BISecurityException {
		String licFilePath = Const.getKettleDirectory() + Const.FILE_SEPARATOR
				+ LIC_FILE_NAME;
		if (!FileUtils.isFileExist(licFilePath)) {
			throw new BISecurityException(BaseMessages.getString(PKG,
					"Lic.Message.No.License"));
		}

	}

	public static boolean checkAppLicense(String licPathName) {
		try {

			// 获得License文件
			InputStream fis = FileUtils.getInputStream(licPathName,
					WebMarshal.class);
			int nDataPos = 0, nFileLen = fis.available();
			byte[] data = new byte[nFileLen];
			if (fis.read(data) != nFileLen) {
				fis.close();
				return false;
			}

			fis.close();

			for (int i = 0; i < nFileLen; i++) {
				if (data[i] == 0) {
					nDataPos = i + 1;
					break;
				}
			}

			String strKey = "ufbq2010";
			SecretKeySpec sksSpec = new SecretKeySpec(strKey.getBytes(),
					"Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
			cipher.init(javax.crypto.Cipher.DECRYPT_MODE, sksSpec);
			byte[] result = cipher.doFinal(data, nDataPos, nFileLen - nDataPos);
			String strResult = new String(result, "UTF-8");

			// System.out.println(strResult);
			// MAC: A20-D7ED1-D7EEFF
			// BeginDate: 2010-10-28
			// UsefulLife: 30
			// License: XXXXX-XXXXX-XXXXX
			String lpszAPKInfo, lpszMAC = "", lpszKey = "";
			// lpszAPKInfo = "APK=" + Comm.GetFileNameFromUrl(strApkPathName,
			// true);
			// lpszAPKInfo += ";ID=" + getFileMD5(strApkPathName);
			int nPos1 = strResult.indexOf("MAC:");
			int nPos2 = strResult.indexOf('\n', nPos1 + 4);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
