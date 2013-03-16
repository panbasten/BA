package com.plywet.platform.bi.web.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.pentaho.di.trans.TransMeta;

public class TransOrJobMetaCache {
	public static Map<String, Map<Long, TransMeta>> transMetaCache = Collections
			.synchronizedMap(new HashMap<String, Map<Long, TransMeta>>());

	public static void putTrans(String repository, Long id, TransMeta transMeta) {
		if (!transMetaCache.containsKey(repository)) {
			Map<Long, TransMeta> repMap = Collections
					.synchronizedMap(new HashMap<Long, TransMeta>());
			transMetaCache.put(repository, repMap);
		}
		transMetaCache.get(repository).put(id, transMeta);
	}

	public static TransMeta getTrans(String repository, Long id) {
		if (transMetaCache.containsKey(repository)) {
			return transMetaCache.get(repository).get(id);
		}
		return null;
	}

	public static TransMeta clearTrans(String repository, Long id) {
		if (transMetaCache.containsKey(repository)) {
			return transMetaCache.get(repository).remove(id);
		}
		return null;
	}
}
