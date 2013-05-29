package com.plywet.platform.lic;

import junit.framework.TestCase;

public class KeyGeneraterTest extends TestCase {

	public void testKeyGenerater() {
		KeyGenerater kg = new KeyGenerater();
		kg.generater("plywet2013");

		System.out.println("-------------------------------");
		System.out.println(kg.getPriKey());
		System.out.println("-------------------------------");
		System.out.println(kg.getPubKey());
		System.out.println("-------------------------------");
	}

}
