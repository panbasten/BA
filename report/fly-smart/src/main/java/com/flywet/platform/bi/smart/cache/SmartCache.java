package com.flywet.platform.bi.smart.cache;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flywet.platform.bi.smart.model.BISmartMeta;

public class SmartCache {
	private static Map<Long, SoftReference<BISmartMeta>> SMART_METAS = new ConcurrentHashMap<Long, SoftReference<BISmartMeta>>();

	public static void put(Long id, BISmartMeta meta) {
		SMART_METAS.put(id, new SoftReference<BISmartMeta>(meta));
	}

	public static BISmartMeta get(Long id) {
		SoftReference<BISmartMeta> obj = SMART_METAS.get(id);
		if (obj != null) {
			if (obj.get() == null) {
				clearCache(id);
				return null;
			}
			return obj.get();
		}
		return null;
	}

	public static void clearCache(Long id) {
		SMART_METAS.remove(id);
	}
}
