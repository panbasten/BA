package com.flywet.platform.bi.pivot.model.data;

import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.exception.BIPivotException;
import com.flywet.platform.bi.pivot.model.IRegionData;
import com.tonbeller.wcf.controller.RequestContext;

public class NormalData implements IRegionData {

	public static final String REGION_DATA_NAME = "Normal";

	private NormalDataData data;

	public static NormalData instance(Node node) throws BIException {
		NormalData pd = new NormalData();

		Node dataNode = XMLHandler.getSubNode(node, "data");
		if (dataNode != null) {
			pd.data = NormalDataData.instance(dataNode);
		}

		return pd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		try {
			JSONObject jo = new JSONObject();
			jo.put(REGION_DATA_TYPE, getTypeName());

			if (data != null) {
				jo.put(PROP_NAME_DATA, data.renderJo(context));
			}

			return jo;
		} catch (Exception e) {
			throw new BIPivotException("渲染普通报表区域数据出现错误.", e);
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
