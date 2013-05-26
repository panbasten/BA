package com.plywet.platform.bi.core.sec;

import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.plywet.platform.bi.core.utils.FileUtils;

public class WebMarshal {

	public static final String OS_NAME = "os.name";
	public static final String OS_ARCH = "os.arch";
	public static final String OS_VER = "os.version";

	public static String[] getSysProperty() {
		return new String[] { System.getProperty(OS_NAME),
				System.getProperty(OS_ARCH), 
				System.getProperty(OS_VER) };
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
