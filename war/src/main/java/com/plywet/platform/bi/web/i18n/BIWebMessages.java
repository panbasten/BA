package com.plywet.platform.bi.web.i18n;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.pentaho.di.i18n.BaseMessages;

public class BIWebMessages {

	private final Logger log = Logger.getLogger(BIWebMessages.class);

	public final static String MSG_PAGE_TITLE = "msg_page_title";
	public final static String MSG_PAGE_COMPANY_NAME = "msg_page_company_name";
	public final static String MSG_PAGE_COMPANY_COPYRIGHT = "msg_page_company_copyright";
	public final static String MSG_PAGE_COMPANY_OTHERS = "msg_page_company_others";

	public final static String[] MSG = new String[] { MSG_PAGE_TITLE,
			MSG_PAGE_COMPANY_NAME, MSG_PAGE_COMPANY_COPYRIGHT,
			MSG_PAGE_COMPANY_OTHERS };

	private static Class<?> PKG = BIWebMessages.class;

	@SuppressWarnings("unchecked")
	public static final JSONObject getMessages() {
		JSONObject jo = new JSONObject();

		for (String msg : MSG) {
			jo.put(msg, BaseMessages.getString(PKG, msg));
		}

		return jo;
	}

}
