package com.flywet.platform.bi.component.ss.model;

import java.util.List;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentObjectInterface;
import com.flywet.platform.bi.component.ss.model.style.ICellStyle;

public class StyleTable implements ComponentObjectInterface {

	private static final long serialVersionUID = 5009193439513453507L;

	private List<ICellStyle> styles;

	public ICellStyle getStyle(int styleIdx) {
		return this.styles.get(styleIdx);
	}

	@Override
	public JSONObject toJSONObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
