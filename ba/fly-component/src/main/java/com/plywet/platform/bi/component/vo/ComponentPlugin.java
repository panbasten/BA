package com.plywet.platform.bi.component.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.core.xml.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.component.core.ComponentAttributeInterface;
import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.core.utils.PropertyUtils;
import com.plywet.platform.bi.core.utils.ReflectionUtils;
import com.plywet.platform.bi.core.utils.Utils;

public class ComponentPlugin {
	private String id;
	private String description;
	private String classname;
	private String category;
	private String categorydesc;
	private String tooltip;
	private String iconfile;
	private boolean ignoreInDesigner;
	private boolean container;
	private ComponentResolverInterface resolver;

	private List<ComponentAttributeInterface> attributes = new ArrayList<ComponentAttributeInterface>();;
	private Map<String, ComponentAttributeInterface> attributesMap = new HashMap<String, ComponentAttributeInterface>();

	private List<ComponentSignal> signals = new ArrayList<ComponentSignal>();

	private List<ComponentSlot> slots = new ArrayList<ComponentSlot>();

	private ComponentPlugin(Node componentNode) throws Exception {
		this.id = XMLHandler.getTagAttribute(componentNode, "id");
		this.description = PropertyUtils.getCodedTranslation(XMLUtils
				.getTagOrAttribute(componentNode, "description"));
		this.iconfile = XMLUtils.getTagOrAttribute(componentNode, "iconfile");
		this.tooltip = PropertyUtils.getCodedTranslation(XMLUtils
				.getTagOrAttribute(componentNode, "tooltip"));
		this.category = XMLUtils.getTagOrAttribute(componentNode, "category");
		this.categorydesc = PropertyUtils
				.getCodedTranslation("i18n:com.plywet.platform.bi.component.resolvers:Component.Category."
						+ this.category);
		this.classname = XMLUtils.getTagOrAttribute(componentNode, "classname");
		this.resolver = (ComponentResolverInterface) ReflectionUtils
				.newInstance(this.classname);

		this.ignoreInDesigner = Utils.toBoolean(XMLUtils.getTagOrAttribute(
				componentNode, "ignoreInDesigner"), false);

		this.container = Utils.toBoolean(XMLUtils.getTagOrAttribute(
				componentNode, "container"), false);

		// 属性
		Node attributesNode = XMLHandler
				.getSubNode(componentNode, "attributes");
		if (attributesNode != null) {
			NodeList children = attributesNode.getChildNodes();
			Node childnode;

			for (int i = 0; i < children.getLength(); i++) {
				childnode = children.item(i);
				if (childnode.getNodeName().equalsIgnoreCase("attribute")) {
					addAttribute(ComponentAttribute.instance(childnode));
				} else if (childnode.getNodeName().equalsIgnoreCase(
						"attributeGroup")) {
					addAttributeGroup(ComponentAttributeGroup
							.instance(childnode));
				}
			}
		}

		// 信号
		Node signalsNode = XMLHandler.getSubNode(componentNode, "signals");
		if (signalsNode != null) {
			NodeList children = signalsNode.getChildNodes();
			Node childnode;

			for (int i = 0; i < children.getLength(); i++) {
				childnode = children.item(i);
				if (childnode.getNodeName().equalsIgnoreCase("signal")) {
					addSignal(ComponentSignal.instance(childnode));
				}
			}
		}

		// 槽
		Node slotsNode = XMLHandler.getSubNode(componentNode, "slots");
		if (slotsNode != null) {
			NodeList children = slotsNode.getChildNodes();
			Node childnode;

			for (int i = 0; i < children.getLength(); i++) {
				childnode = children.item(i);
				if (childnode.getNodeName().equalsIgnoreCase("slot")) {
					addSlot(ComponentSlot.instance(childnode));
				}
			}
		}
	}

	/**
	 * 获得JSON格式的属性集
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getAttributesJSONArray() {
		JSONArray ja = new JSONArray();
		for (ComponentAttributeInterface attr : attributes) {
			ja.add(attr.toJSONObject());
		}
		return ja;
	}

	/**
	 * 获得JSON格式的信号集
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getSignalsJSONArray() {
		if (!signals.isEmpty()) {
			JSONArray ja = new JSONArray();
			for (ComponentSignal s : signals) {
				ja.add(s.toJSONObject());
			}
			return ja;
		}
		return null;
	}

	/**
	 * 获得JSON格式的槽集
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JSONArray getSlotsJSONArray() {
		if (!slots.isEmpty()) {
			JSONArray ja = new JSONArray();
			for (ComponentSlot s : slots) {
				ja.add(s.toJSONObject());
			}
			return ja;
		}
		return null;
	}

	public void setDefaultAttributesForNode(Node node) {
		for (ComponentAttributeInterface attr : attributes) {
			if (attr instanceof ComponentAttributeGroup) {
				setDefaultAttributeGroup(node, (ComponentAttributeGroup) attr);
			} else if (attr instanceof ComponentAttribute) {
				setDefaultAttribute(node, (ComponentAttribute) attr);
			}
		}
	}

	public void setDefaultAttributeGroup(Node node, ComponentAttributeGroup attr) {
		for (ComponentAttribute a : attr.getAttributes()) {
			setDefaultAttribute(node, a);
		}
	}

	public void setDefaultAttribute(Node node, ComponentAttribute attr) {
		String defaultValueStr = attr.getDefaultValueStr();
		if (StringUtils.isNotEmpty(defaultValueStr)) {
			XMLUtils.setAttribute(node, attr.getName(), defaultValueStr);
		}
	}

	public void addAttributeGroup(ComponentAttributeGroup attrGroup) {
		addAttribute(attrGroup);
		for (ComponentAttribute attr : attrGroup.getAttributes()) {
			attributesMap.put(attr.getName().toLowerCase(), attr);
		}

	}

	public void addAttribute(ComponentAttributeInterface attr) {
		attributes.add(attr);
		attributesMap.put(attr.getName().toLowerCase(), attr);
	}

	public ComponentAttributeInterface getAttribute(String name) {
		if (attributesMap != null && name != null) {
			return attributesMap.get(name.toLowerCase());
		}
		return null;
	}

	public boolean containAttribute(String name) {
		if (attributesMap != null && name != null) {
			return attributesMap.containsKey(name.toLowerCase());
		}
		return false;
	}

	public void addSignal(ComponentSignal signal) {
		signals.add(signal);
	}

	public void addSlot(ComponentSlot slot) {
		slots.add(slot);
	}

	public static ComponentPlugin instance(Node componentNode) throws Exception {
		return new ComponentPlugin(componentNode);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategorydesc() {
		return categorydesc;
	}

	public void setCategorydesc(String categorydesc) {
		this.categorydesc = categorydesc;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getIconfile() {
		return iconfile;
	}

	public void setIconfile(String iconfile) {
		this.iconfile = iconfile;
	}

	public ComponentResolverInterface getResolver() {
		return resolver;
	}

	public void setResolver(ComponentResolverInterface resolver) {
		this.resolver = resolver;
	}

	public boolean isIgnoreInDesigner() {
		return ignoreInDesigner;
	}

	public void setIgnoreInDesigner(boolean ignoreInDesigner) {
		this.ignoreInDesigner = ignoreInDesigner;
	}

	public boolean isContainer() {
		return container;
	}

	public void setContainer(boolean container) {
		this.container = container;
	}

}
