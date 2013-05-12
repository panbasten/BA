package com.plywet.platform.bi.component.vo;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.w3c.dom.Node;

import com.plywet.platform.bi.component.core.ComponentAttributeInterface;
import com.plywet.platform.bi.component.resolvers.enums.FLYEnumUtils;
import com.plywet.platform.bi.core.utils.PropertyUtils;
import com.plywet.platform.bi.core.utils.Utils;
import com.plywet.platform.bi.core.utils.XmlUtils;

public class ComponentAttribute extends BaseComponentAttribute implements
		ComponentAttributeInterface {

	private boolean required;
	private Class<?> type;
	private String defaultValueStr;
	private Object defaultValue;

	private ComponentAttribute(Node attributesNode) throws Exception {
		this.name = XmlUtils.getTagOrAttribute(attributesNode, "name");
		this.required = Utils.toBoolean(XmlUtils.getTagOrAttribute(
				attributesNode, "required"), false);

		String typeStr = Utils.NVL(XmlUtils.getTagOrAttribute(attributesNode,
				"type"), "java.lang.String");
		this.type = Class.forName(typeStr);

		this.defaultValueStr = XmlUtils.getTagOrAttribute(attributesNode,
				"defaultValue");
		if (StringUtils.isNotEmpty(defaultValueStr)) {
			if (this.type == String.class) {
				this.defaultValue = String.valueOf(this.defaultValueStr);
			} else if (this.type == Integer.class) {
				this.defaultValue = Integer.parseInt(this.defaultValueStr);
			} else if (this.type == Double.class) {
				this.defaultValue = Double.valueOf(this.defaultValueStr);
			} else if (this.type == Boolean.class) {
				this.defaultValue = Utils
						.toBoolean(this.defaultValueStr, false);
			}
		}

		this.description = PropertyUtils.getCodedTranslation(XmlUtils
				.getTagOrAttribute(attributesNode, "description"));

		this.tooltip = PropertyUtils.getCodedTranslation(XmlUtils
				.getTagOrAttribute(attributesNode, "tooltip"));

		this.editorType = XmlUtils.getTagOrAttribute(attributesNode,
				"editorType");

		this.editorOptionEnum = XmlUtils.getTagOrAttribute(attributesNode,
				"editorOptionEnum");

	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJSONObject() {
		JSONObject jo = new JSONObject();
		jo.put("name", this.name);
		jo.put("description", this.description);
		jo.put("tooltip", this.tooltip);
		jo.put("editorType", this.editorType);

		if (this.editorOptionEnum != null) {
			jo.put("editorOptionEnum", FLYEnumUtils
					.getEnumJSONArray(this.editorOptionEnum));
		}

		jo.put("required", required);
		jo.put("defaultValue", defaultValueStr);
		return jo;
	}

	public static ComponentAttribute instance(Node attributesNode)
			throws Exception {
		return new ComponentAttribute(attributesNode);
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getDefaultValueStr() {
		return defaultValueStr;
	}

	public void setDefaultValueStr(String defaultValueStr) {
		this.defaultValueStr = defaultValueStr;
	}

	@SuppressWarnings("unchecked")
	public <T> T getDefaultValue() {
		return (T) defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

}
