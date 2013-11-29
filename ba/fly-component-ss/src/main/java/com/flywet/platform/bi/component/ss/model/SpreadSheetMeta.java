package com.flywet.platform.bi.component.ss.model;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.ComplexComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class SpreadSheetMeta extends ComplexComponentMeta implements
		ComponentMetaInterface {

	// 当前表格页面宽度
	public static final String GLOBLE_PROP_PAGE_WIDTH = "g_pageWidth";

	// 当前表格页面高度
	public static final String GLOBLE_PROP_PAGE_HEIGHT = "g_pageHeight";

	private Integer pageWidth;
	private Integer pageHeight;

	// 工作薄
	private Workbook workbook;

	// 用户全局属性
	private Map<String, Object> custGlobleAttributes = new HashMap<String, Object>();

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_SPREADSHEET;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormJo() throws BIJSONException {
		this.addExtendAttributes(custGlobleAttributes);

		JSONObject jo = super.getFormJo();
		if (pageWidth != null)
			jo.put("pageWidth", pageWidth);
		if (pageHeight != null)
			jo.put("pageHeight", pageHeight);
		if (workbook != null)
			jo.put("workbook", workbook.toJSONObject());
		
		return jo;
	}

	public int getPageWidth() {
		return pageWidth;
	}

	public void setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
	}

	public int getPageHeight() {
		return pageHeight;
	}

	public void setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
	}

	public void putGlobleAttr(String key, Object val) {
		this.custGlobleAttributes.put(key, val);
	}

	public Object getGlobleAttr(String key) {
		return this.custGlobleAttributes.get(key);
	}

}
