package com.flywet.platform.bi.component.ss.model;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentObjectInterface;
import com.flywet.platform.bi.component.ss.model.style.ICellStyle;
import com.flywet.platform.bi.component.ss.model.theme.ThemesTable;

/**
 * 电子表格工作空间模型
 * 
 * @author PeterPan
 * 
 */
public class Workbook implements ComponentObjectInterface {

	private static final long serialVersionUID = -7714538726045702973L;

	// 工作薄
	private List<Sheet> sheets;

	// 命名区域
	private List<NanedRange> namedRanges;

	// 样式表
	private StyleTable styleTable;

	// 主题表
	private ThemesTable themesTable;

	// 激活的工作薄
	private int activeSheet = 0;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {

		JSONObject jo = new JSONObject();
		if (sheets != null) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < sheets.size(); i++) {
				ja.add(sheets.get(i).toJSONObject());
			}
			jo.put("sheets", ja);
		}

		if (namedRanges != null) {
			JSONArray ja = new JSONArray();
			for (int i = 0; i < namedRanges.size(); i++) {
				ja.add(namedRanges.get(i).toJSONObject());
			}
			jo.put("namedRanges", ja);
		}

		if (styleTable != null) {
			jo.put("styleTable", styleTable.toJSONObject());
		}

		if (themesTable != null) {
			jo.put("themesTable", themesTable.toJSONObject());
		}

		jo.put("activeSheet", activeSheet);

		return jo;
	}

	/**
	 * 获得默认的列宽
	 * 
	 * @return
	 */
	public int getDefaultColumnWidth() {
		return this.themesTable.getColWidth();
	}

	/**
	 * 获得默认的行高
	 * 
	 * @return
	 */
	public int getDefaultRowHeight() {
		return this.themesTable.getRowHeight();
	}

	/**
	 * 获得默认的列样式
	 * 
	 * @return
	 */
	public ICellStyle getDefaultColStyle() {
		return this.themesTable.getColStyle();
	}

	/**
	 * 获得默认的行样式
	 * 
	 * @return
	 */
	public ICellStyle getDefaultRowStyle() {
		return this.themesTable.getRowStyle();
	}

	/**
	 * 通过样式索引获得具体样式
	 * 
	 * @param styleIdx
	 * @return
	 */
	public ICellStyle getStyle(int styleIdx) {
		return this.styleTable.getStyle(styleIdx);
	}

}
