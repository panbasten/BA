package com.plywet.platform.bi.component.vo;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.core.xml.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.component.core.ComponentObjectInterface;
import com.plywet.platform.bi.core.utils.PropertyUtils;

public class ComponentSignal implements ComponentObjectInterface {

	private String name;
	private String description;
	private String tooltip;

	private List<ComponentParameter> params = new ArrayList<ComponentParameter>();

	private ComponentSignal(Node node) throws Exception {
		this.name = XMLUtils.getTagOrAttribute(node, "name");
		this.description = PropertyUtils.getCodedTranslation(XMLUtils
				.getTagOrAttribute(node, "description"));
		this.tooltip = PropertyUtils.getCodedTranslation(XMLUtils
				.getTagOrAttribute(node, "tooltip"));

		// 参数
		Node paramsNode = XMLHandler.getSubNode(node, "parameters");
		if (paramsNode != null) {
			NodeList children = paramsNode.getChildNodes();
			Node childnode;

			for (int i = 0; i < children.getLength(); i++) {
				childnode = children.item(i);
				if (childnode.getNodeName().equalsIgnoreCase("parameter")) {
					addParameter(ComponentParameter.instance(childnode));
				}
			}
		}
	}

	public static ComponentSignal instance(Node node) throws Exception {
		return new ComponentSignal(node);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("name", this.name);
		jo.put("description", this.description);
		jo.put("tooltip", this.tooltip);
		jo.put("displayName", getShowName());
		return jo;
	}
	
	public String getShowName() {
		String rtn = this.description;
		if (!this.params.isEmpty()) {
			rtn += "[";
			rtn += this.params.get(0).getDescription();
			for (int i = 1; i < this.params.size(); i++) {
				rtn += ", ";
				rtn += this.params.get(i).getDescription();
			}
			rtn += "]";
		}
		return rtn;
	}

	public void addParameter(ComponentParameter params) {
		this.params.add(params);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

}
