package com.plywet.platform.bi.component.vo;

import org.json.simple.JSONObject;
import org.pentaho.di.core.xml.XMLUtils;
import org.w3c.dom.Node;

import com.plywet.platform.bi.component.core.ComponentObjectInterface;
import com.plywet.platform.bi.core.utils.PropertyUtils;

public class ComponentParameter implements ComponentObjectInterface {

	private String name;
	private String description;
	private String tooltip;

	private ComponentParameter(Node node) {
		this.name = XMLUtils.getTagOrAttribute(node, "name");
		this.description = PropertyUtils.getCodedTranslation(XMLUtils
				.getTagOrAttribute(node, "description"));
		this.tooltip = PropertyUtils.getCodedTranslation(XMLUtils
				.getTagOrAttribute(node, "tooltip"));
	}

	public static ComponentParameter instance(Node node) throws Exception {
		return new ComponentParameter(node);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("name", this.name);
		jo.put("description", this.description);
		jo.put("tooltip", this.tooltip);
		return jo;
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
