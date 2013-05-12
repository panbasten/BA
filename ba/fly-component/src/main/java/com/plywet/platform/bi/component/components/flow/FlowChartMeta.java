package com.plywet.platform.bi.component.components.flow;

import org.json.simple.JSONObject;

import com.plywet.platform.bi.component.components.BaseComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIJSONException;

public class FlowChartMeta extends BaseComponentMeta implements
		ComponentMetaInterface {

	private FlowChartData data;

	public void init() {
		data = new FlowChartData();
		data.setElementSet(new FlowElementSet());
		data.setDefaultAttributes(new FlowDefaultAttributes());
	}

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_FLOWCHART;
	}

	@Override
	public JSONObject getFormJo() throws BIJSONException {
		super.setData(data);
		return super.getFormJo();
	}

	@Override
	public void removeAll() {
		super.removeAll();
		this.data = null;
	}

	public FlowChartData getFlowChartData() {
		return data;
	}

	public void setFlowChartData(FlowChartData data) {
		this.data = data;
	}

}
