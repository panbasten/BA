package com.yonyou.bq8.di.component.components.tab;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.BaseComponentMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

public class BQTabViewMeta extends BaseComponentMeta implements
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
	public JSONObject getFormJo() throws DIJSONException {
		JSONObject jo = super.getFormJo();
		JSONArray ja = new JSONArray();
		for (TabNode t : tabs) {
			ja.add(t.getFormJo());
		}
		jo.put("tabs", ja);
		return jo;
	}

}
