package com.flywet.platform.bi.component.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.flywet.platform.bi.core.utils.Utils;

public class PageTemplateCache {
	public static Map<String, String> cache = Collections
			.synchronizedMap(new HashMap<String, String>());

	public static void put(String url, String domString) {
		cache.put(url, domString);
	}

	public static String getDomByUrl(String url) {
		if (Utils.isApplicationDebug()) {
			return null;
		}
		return cache.get(url);
	}
}
