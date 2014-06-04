package com.flywet.platform.bi.pivot.model.chart.attrs;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.component.vo.ComponentFunction;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.IJSONObjectable;
import com.tonbeller.wcf.controller.RequestContext;

public class Tooltip implements IJSONObjectable {
	ComponentFunction formatter;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (formatter != null) {
			jo.put("formatter", formatter);
		}

		return jo;
	}

	public static Tooltip instance(Node node) {
		Tooltip t = new Tooltip();

		Node formatterNode = XMLHandler.getSubNode(node, "formatter");
		if (formatterNode != null) {
			t.formatter = ComponentFunction.instance();
			t.formatter.addStatement(Const.removeTAB(Const
					.removeCRLF(XMLHandler.getNodeValue(formatterNode))));
		}

		return t;
	}
}
