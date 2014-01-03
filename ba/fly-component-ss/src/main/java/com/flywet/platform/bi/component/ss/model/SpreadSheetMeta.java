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

	// 自定义表格页面尺寸
	private Integer width;
	private Integer height;

	// 是否显示
	private Boolean show;

	// 是否自适应大小
	private Boolean fit;

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
		if (width != null)
			jo.put("width", width);
		if (height != null)
			jo.put("height", height);
		if (show != null)
			jo.put("show", show);
		if (fit != null)
			jo.put("fit", fit);
		if (workbook != null)
			jo.put("workbook", workbook.toJSONObject());

		return jo;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public Boolean getFit() {
		return fit;
	}

	public void setFit(boolean fit) {
		this.fit = fit;
	}

	public void putGlobleAttr(String key, Object val) {
		this.custGlobleAttributes.put(key, val);
	}

	public Object getGlobleAttr(String key) {
		return this.custGlobleAttributes.get(key);
	}

}
