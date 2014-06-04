package com.flywet.platform.bi.pivot.model.chart.attrs;

import java.util.Map;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.component.vo.ComponentFunction;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.model.IJSONObjectable;
import com.tonbeller.wcf.controller.RequestContext;

public class Label implements IJSONObjectable {
	Map<String, String> attrs;

	Boolean enabled;

	ComponentFunction formatter;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (attrs != null) {
			jo.putAll(attrs);
		}

		if (enabled != null) {
			jo.put("enabled", enabled);
		}

		if (formatter != null) {
			jo.put("formatter", formatter);
		}

		return jo;
	}

	public static Label instance(Node node) {
		Label l = new Label();

		Map<String, String> attrsMap = XMLHandler.getNodeAttributesMap(node);
		if (attrsMap != null) {
			l.attrs = attrsMap;
			l.enabled = Utils.toBoolean(attrsMap.get("enabled"), null);
		}

		Node formatterNode = XMLHandler.getSubNode(node, "formatter");
		if (formatterNode != null) {
			l.formatter = ComponentFunction.instance();
			l.formatter.addStatement(Const.removeTAB(Const
					.removeCRLF(XMLHandler.getNodeValue(formatterNode))));
		}

		return l;
	}
}
