package com.flywet.platform.bi.report.model;

import java.util.HashMap;
import java.util.Map;

import com.flywet.platform.bi.report.style.SpreadSheetStyle;

public class SpreadSheetMeta {

	// 表格是否为无限表的标识。
	public static final String GLOBLE_PROP_INFINITE = "g_infinite";

	// 最小表格行数
	public static final String GLOBLE_PROP_MIN_ROW_SIZE = "g_minRowSize";

	// 最小表格列数
	public static final String GLOBLE_PROP_MIN_COL_SIZW = "g_minColSize";

	// 当前表格总行数
	public static final String GLOBLE_PROP_ROW_SIZE = "g_rowSize";

	// 当前表格总列数
	public static final String GLOBLE_PROP_COL_SIZW = "g_colSize";
	
	// 当前表格总列数
	public static final String GLOBLE_PROP_PAGE_WIDTH = "g_pageWidth";
	
	// 当前表格总列数
	public static final String GLOBLE_PROP_PAGE_HEIGHT = "g_pageHeight";
	
	

	// 全局属性
	private Map<String, Object> globleProperties = new HashMap<String, Object>();

	private SpreadSheetStyle globleRowStyle, globleColStyle;
}
