package com.plywet.platform.bi.sec;

import com.plywet.platform.bi.core.sec.WebMarshal;

import junit.framework.TestCase;

public class WebMarshalTest extends TestCase {

	public void testCheckAppLicense() {
		boolean check = WebMarshal.checkAppLicense("JServer.lic");

		System.out.println(check);
	}

	public void testGetSysProperty() {
		String[] props = WebMarshal.getSysProperty();
		for (String p : props) {
			System.out.println(p);
		}
	}
}
