package com.plywet.platform.bi.web.i18n;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.pentaho.di.i18n.BaseMessages;

public class BIWebMessages {

	private final Logger log = Logger.getLogger(BIWebMessages.class);

	private static Class<?> PKG = BIWebMessages.class;

	public static final JSONObject getMessages() {
		JSONObject jo = new JSONObject();

		BaseMessages.getString(PKG, "");

		return jo;
	}

}
