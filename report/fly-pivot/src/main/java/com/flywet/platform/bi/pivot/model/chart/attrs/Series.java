package com.flywet.platform.bi.pivot.model.chart.attrs;

import java.util.Map;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.model.IJSONObjectable;
import com.tonbeller.wcf.controller.RequestContext;

public class Series implements IJSONObjectable {

	Map<String, String> attrs;

	Integer yAxis;

	String data;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (attrs != null) {
			jo.putAll(attrs);
		}

		if (yAxis != null) {
			jo.put("yAxis", yAxis);
		}

		if (data != null) {
			try {
				jo.put("data", JSONUtils.convertStringToJSONArray(data));
			} catch (Exception e) {
				throw new BIException("解析Series的data属性出现问题。", e);
			}
		}

		return jo;
	};

	public static Series instance(Node node) {
		Series s = new Series();

		Map<String, String> attrsMap = XMLHandler.getNodeAttributesMap(node);
		if (attrsMap != null) {
			s.attrs = attrsMap;
			s.yAxis = Utils.toInt(attrsMap.get("yAxis"), null);
		}

		Node dataNode = XMLHandler.getSubNode(node, "data");
		if (dataNode != null) {
			s.data = Const.removeTAB(Const.removeCRLF(XMLHandler
					.getNodeValue(dataNode)));
		}

		return s;
	}
}
