package com.flywet.platform.lic;

import java.util.List;

import junit.framework.TestCase;

public class SignProviderTest extends TestCase {
	public static final String KEY = "flywet2013";
	public static final String PLAIN_TEXT = "abcdefg";

	public void testVerify() {
		KeyGenerater kg = new KeyGenerater();
		kg.generater(KEY);
		
		System.out.println("-----------pubKey----------");
		System.out.println(kg.getPubKey());

		String signText = Signaturer.sign(kg.getPriKey(), PLAIN_TEXT);

		boolean v = SignProvider.verify(kg.getPubKey(), PLAIN_TEXT, signText);
		assertTrue(v);

		v = SignProvider.verify(kg.getPubKey(), PLAIN_TEXT + "1", signText);
		assertFalse(v);

	}

	public void testDecodeLicense() {
		LicenseGenerator lg = LicenseGenerator.instance().setCustomerFullName(
				"Flywet研发中心").setMacAddress("00:11:22:33:44:55,11:11:22:33:44:55");
		lg.addLicense(101);
		lg.addLicense(201, 30);

		try {
			String licText = lg.getLicenseText();

			List<String[]> decode = SignProvider.decodeLicense(licText);

			assertEquals(decode.size(), 3);
			
			assertEquals(decode.get(0)[0], "Flywet研发中心");
			assertEquals(decode.get(0)[1], "00:11:22:33:44:55,11:11:22:33:44:55");
			
			assertEquals(decode.get(1)[0], "101");

			System.out.println(decode);
		} catch (BILicenseException e) {
			e.printStackTrace();
		}
	}
}
