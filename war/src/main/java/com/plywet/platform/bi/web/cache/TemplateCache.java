package com.plywet.platform.bi.web.cache;

import java.util.HashMap;
import java.util.Map;

import com.plywet.platform.bi.report.meta.TemplateMeta;

import edu.emory.mathcs.backport.java.util.Collections;

public class TemplateCache {

	private static Map<String, TemplateCacheObject> DASHBOARD_TEMPLATES = Collections
			.synchronizedMap(new HashMap<String, TemplateCacheObject>());

	public static void put(String id, TemplateMeta meta) {
		DASHBOARD_TEMPLATES.put(id, new TemplateCacheObject(meta));
	}

	public static TemplateMeta get(String id) {
		TemplateCacheObject obj = DASHBOARD_TEMPLATES.get(id);
		if (obj != null) {
			return obj.getTemplateMeta();
		}
		return null;
	}
}

class TemplateCacheObject {
	// TODO
	private String user;

	private TemplateMeta templateMeta;

	TemplateCacheObject(TemplateMeta templateMeta) {
		this.templateMeta = templateMeta;
	}

	public TemplateMeta getTemplateMeta() {
		return templateMeta;
	}

	public String getUser() {
		return user;
	}

}