package com.flywet.platform.bi.web.cache;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flywet.platform.bi.dashboard.model.TemplateMeta;

public class TemplateCache {

	private static Map<String, SoftReference<TemplateMeta>> DASHBOARD_TEMPLATES = new ConcurrentHashMap<String, SoftReference<TemplateMeta>>();

	public static void put(String id, TemplateMeta meta) {
		DASHBOARD_TEMPLATES.put(id, new SoftReference<TemplateMeta>(meta));
	}

	public static TemplateMeta get(String id) {
		SoftReference<TemplateMeta> obj = DASHBOARD_TEMPLATES.get(id);
		if (obj != null) {
			if (obj.get() == null) {
				clearCache(id);
				return null;
			}
			return obj.get();
		}
		return null;
	}

	public static void clearCache(String id) {
		DASHBOARD_TEMPLATES.remove(id);
	}
}