package com.flywet.platform.bi.pivot.model.chart.attrs;

import java.util.Map;

import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.pivot.model.IJSONObjectable;
import com.tonbeller.wcf.controller.RequestContext;

public class Title implements IJSONObjectable {

	Map<String, String> attrs;

	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		if (attrs != null) {
			return JSONUtils.convertToJSONObject(attrs);
		}
		return null;
	}

	public static Title instance(Node node) {
		Title t = new Title();
		Map<String, String> map = XMLHandler.getNodeAttributesMap(node);
		t.attrs = map;
		return t;
	}

}
