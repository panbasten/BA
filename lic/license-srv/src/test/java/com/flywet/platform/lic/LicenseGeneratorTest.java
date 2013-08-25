package com.flywet.platform.lic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class LicenseGeneratorTest extends TestCase {

	public void testCreateTrialLicenseFile() {
		LicenseGenerator lg = LicenseGenerator.instance();
		lg.setTrialVersion();
		createLicenseFile(lg, "trial");
	}
	
	public void testCreateTrialLicenseFile1111() {
		LicenseGenerator lg = LicenseGenerator.instance();
		lg.setTrialVersion();
		lg.setMacAddress("00-E0-81-CD-36-A3");
		createLicenseFile(lg, "trial1");
	}

	public void testCreateBaseLicenseFile() {
		LicenseGenerator lg = LicenseGenerator.instance();
		lg.setBaseVersion();
		createLicenseFile(lg, "base");
	}

	public void testCreateLicenseFile() {
		LicenseGenerator lg = LicenseGenerator.instance();
		lg.setCustomerFullName("只用DI的用户");
		lg.setAllMacAddress();
		lg.addModuleLicenses(LicenseEnums.LIC_CATEGORY_SYS);
		lg.addModuleLicenses(LicenseEnums.LIC_CATEGORY_DI);
		createLicenseFile(lg, "di");
	}

	private void createLicenseFile(LicenseGenerator lg, String subfix) {
		try {
			String lic = lg.getLicenseText();

			File licFile = new File("c:/_d/ba.lic." + subfix);
			if (licFile.exists()) {
				licFile.delete();
			}

			FileOutputStream out = null;
			try {
				out = new FileOutputStream(licFile);
				out.write(lic.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} catch (BILicenseException e) {
			e.printStackTrace();
		}
	}
}
