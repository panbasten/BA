package com.flywet.platform.lic;

import junit.framework.TestCase;

public class KeyGeneraterTest extends TestCase {

	public void testKeyGenerater() {
		KeyGenerater kg = new KeyGenerater();
//		kg.generater("flywet2013");
		kg.generater("FLYWET@2013");

		System.out.println("------------kg.getPriKey testKeyGenerater-------------------");
		System.out.println(kg.getPriKey());
		System.out.println("------------kg.getPubKey testKeyGenerater-------------------");
		System.out.println(kg.getPubKey());
		System.out.println("-------------------------------");
		
		byte[] bb = new byte[] { 'a', 'b', 'c', 'b' };
		System.out.println("---------------'a', 'b', 'c', 'b'----------------");
		System.out.println(Base64.encodeBytes(bb));
	}

}
