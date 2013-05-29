package com.plywet.platform.lic;

import junit.framework.TestCase;

public class SignaturerTest extends TestCase {

	public void testSign() {
		KeyGenerater kg = new KeyGenerater();
		kg.generater("plywet2013");

		System.out.println("--------------sign---------------");
		System.out.println(Signaturer.sign(kg.getPriKey(), "abcdefg"));
		System.out.println("--------------sign---------------");
	}
}
