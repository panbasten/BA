package com.flywet.platform.bi.pivot.model.dataformat;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.Utils;
import com.flywet.platform.bi.pivot.model.IPivotReport;
import com.flywet.platform.bi.pivot.model.enums.ConditionEnum;
import com.tonbeller.wcf.controller.RequestContext;

public class IconDataFormat implements IDataFormat {

	public static final String DATA_FORMAT_TYPE = "icon";

	private String name;

	private List<IconCondition> conditions;

	public static IconDataFormat instance(Node node) throws BIException {
		try {
			IconDataFormat idf = new IconDataFormat();

			idf.name = Const
					.NVL(XMLHandler.getTagAttribute(node, "name"), null);

			List<Node> conditionNodes = XMLHandler.getNodes(node, "condition");
			if (conditionNodes != null && conditionNodes.size() > 0) {
				idf.conditions = new ArrayList<IconCondition>();
				for (Node n : conditionNodes) {
					idf.conditions.add(IconCondition.instance(n));
				}
			}

			return idf;

		} catch (Exception e) {
			throw new BIException("实例化图标数据格式出现错误：IconDataFormat");
		}

	}

	@Override
	public void init(RequestContext context) throws BIException {

	}

	@Override
	public Object findByName(String name) throws BIException {
		if (name.equals(this.name)) {
			return this;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();
		jo.put("type", DATA_FORMAT_TYPE);
		jo.put("name", getName());

		if (conditions != null) {
			JSONArray ja = new JSONArray();
			for (IconCondition c : conditions) {
				ja.add(c.renderJo(context));
			}
			jo.put("conditions", ja);
		}
		return jo;
	}

	@Override
	public String getName() {
		return name;
	}

}

class IconCondition implements IPivotReport {

	private String icon;

	private String val1;

	private ConditionEnum oper1;

	private String val2;

	private ConditionEnum oper2;

	private String condtion;

	public static IconCondition instance(Node node) throws BIException {
		try {
			IconCondition ic = new IconCondition();

			ic.icon = Const.NVL(XMLHandler.getTagAttribute(node, "icon"), null);

			ic.val1 = Const.NVL(XMLHandler.getTagAttribute(node, "val1"), null);
			ic.val2 = Const.NVL(XMLHandler.getTagAttribute(node, "val2"), null);

			Integer oper1Int = Utils.toInt(
					XMLHandler.getTagAttribute(node, "oper1"), null);
			if (oper1Int != null) {
				ic.oper1 = ConditionEnum.get(oper1Int.shortValue());
			}

			Integer oper2Int = Utils.toInt(
					XMLHandler.getTagAttribute(node, "oper2"), null);
			if (oper2Int != null) {
				ic.oper2 = ConditionEnum.get(oper2Int.shortValue());
			}

			ic.condtion = Const.NVL(
					XMLHandler.getTagAttribute(node, "condtion"), null);

			return ic;

		} catch (Exception e) {
			throw new BIException("实例化图标数据格式条件出现错误：IconCondition");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject renderJo(RequestContext context) throws BIException {
		JSONObject jo = new JSONObject();

		if (icon != null) {
			jo.put("icon", icon);
		}

		if (condtion != null) {
			jo.put("condtion", condtion);
		} else {
			String c = "";
			if (oper1 != null) {
				c = oper1.getCondition("value", wrapVal(val1));
			}

			if (oper2 != null) {
				if (!Const.isEmpty(c)) {
					c = c + "&&";
				}
				c = c + oper2.getCondition("value", wrapVal(val2));
			}
			jo.put("condtion", c);
		}

		return jo;
	}

	private String wrapVal(String val) {
		val = Const.NVL(val, "");
		if (Utils.isNumber(val)) {
			return val;
		} else {
			return "'" + val + "'";
		}
	}

	@Override
	public void init(RequestContext context) throws BIException {

	}

	@Override
	public Object findByName(String name) throws BIException {
		return null;
	}

}
