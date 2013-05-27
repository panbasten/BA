package com.plywet.platform.lic;

import junit.framework.TestCase;

public class SignProviderTest extends TestCase {
	public static final String KEY = "plywet2013";
	public static final String PLAIN_TEXT = "abcdefg";

	public void testVerify() {
		KeyGenerater kg = new KeyGenerater();
		kg.generater(KEY);

		String signText = Signaturer.sign(kg.getPriKey(), PLAIN_TEXT);

		boolean v = SignProvider.verify(kg.getPubKey(), PLAIN_TEXT, signText);
		assertTrue(v);

		v = SignProvider.verify(kg.getPubKey(), PLAIN_TEXT + "1", signText);
		assertFalse(v);
	}
}
