package com.yonyou.bq8.di.component.components.flow;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.BaseComponentMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

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
	public JSONObject getFormJo() throws DIJSONException {
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
