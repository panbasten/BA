package com.flywet.platform.bi.web.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.pentaho.di.trans.TransMeta;

import com.flywet.platform.bi.core.ContextHolder;

public class TransOrJobMetaCache {
	static Map<String, Map<Long, TransMeta>> TRANS_META_CACHE = Collections
			.synchronizedMap(new HashMap<String, Map<Long, TransMeta>>());

	public static void putTrans(Long id, TransMeta transMeta) {
		putTrans(ContextHolder.getRepositoryName(), id, transMeta);
	}

	public static void putTrans(String repository, Long id, TransMeta transMeta) {
		if (!TRANS_META_CACHE.containsKey(repository)) {
			Map<Long, TransMeta> repMap = Collections
					.synchronizedMap(new HashMap<Long, TransMeta>());
			TRANS_META_CACHE.put(repository, repMap);
		}
		TRANS_META_CACHE.get(repository).put(id, transMeta);
	}

	public static TransMeta getTrans(Long id) {
		return getTrans(ContextHolder.getRepositoryName(), id);
	}

	public static TransMeta getTrans(String repository, Long id) {
		if (TRANS_META_CACHE.containsKey(repository)) {
			return TRANS_META_CACHE.get(repository).get(id);
		}
		return null;
	}

	public static TransMeta clearTrans(Long id) {
		return clearTrans(ContextHolder.getRepositoryName(), id);
	}

	public static TransMeta clearTrans(String repository, Long id) {
		if (TRANS_META_CACHE.containsKey(repository)) {
			return TRANS_META_CACHE.get(repository).remove(id);
		}
		return null;
	}
}
