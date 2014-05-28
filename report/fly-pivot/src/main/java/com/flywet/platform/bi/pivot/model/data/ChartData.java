package com.flywet.platform.bi.pivot.model.data;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.exception.BIPivotException;
import com.flywet.platform.bi.pivot.model.IRegionData;
import com.flywet.platform.bi.pivot.model.def.DefaultSetting;
import com.flywet.platform.bi.pivot.model.enums.ChartTypeEnum;
import com.tonbeller.wcf.controller.RequestContext;

public class ChartData implements IRegionData {
	public static final String REGION_DATA_NAME = "Chart";

	public static final String PROP_NAME_CHART_TYPE = "type";

	private ChartDataData data;

	private ChartTypeEnum type;

	public static ChartData instance(Node node) throws BIException {
		ChartData pd = new ChartData();

		Node dataNode = XMLHandler.getSubNode(node, "chart");
		if (dataNode != null) {
			pd.data = ChartDataData.instance(dataNode);
		}

		String chartTypeStr = Const.NVL(
				XMLHandler.getTagAttribute(node, PROP_NAME_CHART_TYPE), null);
		if (chartTypeStr != null) {
			pd.type = ChartTypeEnum.getByName(chartTypeStr);
		} else {
			pd.type = DefaultSetting.DEFAULT_CHART_TYPE;
		}

		return pd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		try {
			JSONObject jo = new JSONObject();
			jo.put(REGION_DATA_TYPE, getTypeName());

			if (type != null) {
				jo.put(PROP_NAME_CHART_TYPE, type.getChartName());
			}

			if (data != null) {
				jo.put(PROP_NAME_DATA, data.renderJo(context));
			}

			return jo;
		} catch (Exception e) {
			throw new BIPivotException("渲染统计图报表区域数据出现错误.", e);
		}
	}

	@Override
	public void init(RequestContext context) throws BIException {
		// TODO Auto-generated method stub

	}

	@Override
	public void flush(RequestContext context) throws BIException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTypeName() {
		return REGION_DATA_NAME;
	}
}
