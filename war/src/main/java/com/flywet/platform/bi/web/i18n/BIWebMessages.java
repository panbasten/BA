package com.flywet.platform.bi.web.i18n;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.i18n.GlobalMessages;

public class BIWebMessages {

	private final Logger log = Logger.getLogger(BIWebMessages.class);

	private static Class<?> PKG = BIWebMessages.class;

	@SuppressWarnings("unchecked")
	public static final JSONObject getMessages() {
		JSONObject jo = new JSONObject();

		ResourceBundle res = GlobalMessages.getBundle(GlobalMessages
				.buildBundleName(PKG.getPackage().getName()), PKG);

		for (String msg : res.keySet()) {
			jo.put(msg, BaseMessages.getString(PKG, msg));
		}

		return jo;
	}

}
