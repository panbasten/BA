package com.flywet.platform.bi.pivot.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.flywet.platform.bi.pivot.model.PivotReport;

public class BIPivotReportCache {

	static Map<Long, PivotReport> PIVOT_REPORT_EDITOR_CACHE = Collections
			.synchronizedMap(new HashMap<Long, PivotReport>());

	public static void putPivotReportEditor(Long id, PivotReport pivotReport) {
		PIVOT_REPORT_EDITOR_CACHE.put(id, pivotReport);
	}

	public static PivotReport getPivotReportEditor(Long id) {
		return PIVOT_REPORT_EDITOR_CACHE.get(id);
	}

	public static PivotReport clearPivotReportEditor(Long id) {
		return PIVOT_REPORT_EDITOR_CACHE.remove(id);
	}

}
