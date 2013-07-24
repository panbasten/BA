package com.flywet.platform.bi.component.components.tab;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.BaseComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class FLYTabViewMeta extends BaseComponentMeta implements
		ComponentMetaInterface {

	private String id;

	private List<TabNode> tabs = new ArrayList<TabNode>();

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_TAB_VIEW;
	}

	public void addTab(TabNode tab) {
		tabs.add(tab);
	}

	public List<TabNode> getTabs() {
		return tabs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public JSONObject getFormJo() throws BIJSONException {
		JSONObject jo = super.getFormJo();
		JSONArray ja = new JSONArray();
		for (TabNode t : tabs) {
			ja.add(t.getFormJo());
		}
		jo.put("tabs", ja);
		return jo;
	}

}
