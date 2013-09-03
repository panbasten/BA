package com.flywet.platform.lic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import junit.framework.TestCase;

public class LicenseGeneratorTest extends TestCase {

	public static final String PROP_CUSTOMER_FULL_NAME = "Customer.Full.Name";
	public static final String PROP_MAC_ADDRESS = "Mac.Address";

	private String priKey;

	private String getPriKey() {
		if (priKey == null) {
			try {
				priKey = SignProvider.getFileString("/ba.prikey");
			} catch (IOException e) {
				e.printStackTrace();
				priKey = "";
			}
		}
		return priKey;
	}

	private String getCustomerPriKey() {
		try {
			return SignProvider.getFileString("/ba.prikey.customer");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "--Bad Private Key--";
	}

	public void testCreateTrialLicenseFile() throws IOException {
		LicenseGenerator lg = LicenseGenerator.instance();
		lg.setTrialVersion();
		lg.setPriKey(getPriKey());

		createLicenseFile(lg, "trial");
	}

	public void testCreateTrialLicenseFile1111() {
		LicenseGenerator lg = LicenseGenerator.instance();
		lg.setTrialVersion();
		lg.setMacAddress("00-E0-81-CD-36-A3");
		lg.setPriKey(getPriKey());

		createLicenseFile(lg, "trial1");
	}

	public void testCreateBaseLicenseFile() {
		LicenseGenerator lg = LicenseGenerator.instance();
		lg.setBaseVersion();
		lg.setPriKey(getPriKey());

		createLicenseFile(lg, "base");
	}

	public void testCreateDILicenseFile() {
		LicenseGenerator lg = LicenseGenerator.instance();
		lg.setCustomerFullName("只用DI的用户");
		lg.setAllMacAddress();
		lg.addModuleLicenses(LicenseEnums.LIC_CATEGORY_SYS);
		lg.addModuleLicenses(LicenseEnums.LIC_CATEGORY_DI);
		lg.setPriKey(getPriKey());

		createLicenseFile(lg, "di");
	}

	public void testCreateOfficialLicenseFile() throws IOException {
		LicenseGenerator lg = LicenseGenerator.instance();
		Properties prop = SignProvider.getProperties("/ba.properties");

		lg.setAllOfficialVersion(prop.getProperty(PROP_CUSTOMER_FULL_NAME),
				prop.getProperty(PROP_MAC_ADDRESS));
		lg.setPriKey(getCustomerPriKey());

		createLicenseFile(lg, "official");
	}

	private void createLicenseFile(LicenseGenerator lg, String subfix) {
		try {
			String lic = lg.getLicenseText();

			File licFile = new File(System.getProperty("user.home")
					+ "/.flywet/ba.lic." + subfix);
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
