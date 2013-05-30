package com.plywet.platform.lic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class LicenseGeneratorTest extends TestCase {

	public void testCreateTrialLicenseFile() {

		LicenseGenerator lg = LicenseGenerator.instance();
		lg.setTrialVersion();

		try {
			String lic = lg.getLicenseText();

			File licFile = new File("c:/_d/ba.lic");
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
