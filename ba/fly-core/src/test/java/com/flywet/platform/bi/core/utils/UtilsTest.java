package com.flywet.platform.bi.core.utils;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

	public void testS10t26() {
		assertEquals(Utils.s26t10("A"), 1);
		assertEquals(Utils.s26t10("AA"), 27);
		assertEquals(Utils.s26t10("A"), 1);
		assertEquals(Utils.s26t10("AZ"), 52);

		assertEquals(Utils.s10t26(1), "A");
		assertEquals(Utils.s10t26(27), "AA");
	}

	public void testIsColor() {
		String s1 = "#fff", s2 = "#12f", s3 = "f00", s4 = "ffd", ss1 = "#ffffff", ss2 = "#1d2f3e", ss3 = "ff00ff", ss4 = "000000";
		String e1 = "#fffdd", e2 = "ifd", e3 = "ff00", e4 = "aabbccd";

		assertTrue(Utils.isColor(s1));
		assertTrue(Utils.isColor(s2));
		assertTrue(Utils.isColor(s3));
		assertTrue(Utils.isColor(s4));

		assertTrue(Utils.isColor(ss1));
		assertTrue(Utils.isColor(ss2));
		assertTrue(Utils.isColor(ss3));
		assertTrue(Utils.isColor(ss4));

		assertFalse(Utils.isColor(e1));
		assertFalse(Utils.isColor(e2));
		assertFalse(Utils.isColor(e3));
		assertFalse(Utils.isColor(e4));

	}
}
