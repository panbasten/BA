package com.flywet.platform.bi.pivot.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tonbeller.jpivot.olap.model.OlapModel;

public class BIOlapModelCache {

	static Map<Long, OlapModel> OLAP_MODEL_CACHE = Collections
			.synchronizedMap(new HashMap<Long, OlapModel>());

	public static void putOlapModel(Long id, OlapModel transMeta) {
		OLAP_MODEL_CACHE.put(id, transMeta);
	}

	public static OlapModel getOlapModel(Long id) {
		return OLAP_MODEL_CACHE.get(id);
	}

	public static OlapModel clearOlapModel(Long id) {
		return OLAP_MODEL_CACHE.remove(id);
	}

}
