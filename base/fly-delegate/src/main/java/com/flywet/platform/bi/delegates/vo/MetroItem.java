package com.flywet.platform.bi.delegates.vo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;

import com.flywet.platform.bi.component.vo.NameValuePair;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.delegates.enums.BIMetroCategory;

public class MetroItem {
	private long id;
	private String data;
	private BIMetroCategory type;

	private List<NameValuePair> extAttrs = new ArrayList<NameValuePair>();

	public void addExtAttr(NameValuePair attr) {
		extAttrs.add(attr);
	}

	@SuppressWarnings("unchecked")
	public JSONObject getJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("itemType", type.getCategory());
		jo.put("itemData", Const.NVL(data, ""));

		for (NameValuePair nv : extAttrs) {
			if ("colspan".equals(nv.getName())
					|| "rowspan".equals(nv.getName())) {
				jo.put(nv.getName(), Utils.toInt(nv.getValue(), null));
			} else {
				jo.put(nv.getName(), Const.NVL(nv.getValue(), null));
			}
		}

		return jo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public BIMetroCategory getType() {
		return type;
	}

	public void setType(BIMetroCategory type) {
		this.type = type;
	}

	public List<NameValuePair> getExtAttrs() {
		return extAttrs;
	}

	public void setExtAttrs(List<NameValuePair> extAttrs) {
		this.extAttrs = extAttrs;
	}

}
