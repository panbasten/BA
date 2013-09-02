package com.flywet.platform.bi.core.sec;

import junit.framework.TestCase;

public class KeyGeneraterTest extends TestCase {

	public void testKeyGenerater() {
		KeyGenerater kg = new KeyGenerater();
		kg.generater("FLYWET@2013");

		System.out.println("------------kg.getPriKey testKeyGenerater-------------------");
		System.out.println(kg.getPriKey());
		System.out.println("------------kg.getPubKey testKeyGenerater-------------------");
		System.out.println(kg.getPubKey());
		System.out.println("-------------------------------");
	}

}
