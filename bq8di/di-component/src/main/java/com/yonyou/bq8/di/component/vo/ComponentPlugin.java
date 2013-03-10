package com.yonyou.bq8.di.component.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.core.utils.PropertyUtils;
import com.yonyou.bq8.di.core.utils.ReflectionUtils;
import com.yonyou.bq8.di.core.utils.XmlUtils;

public class ComponentPlugin {
	private String id;
	private String description;
	private String classname;
	private String category;
	private String categorydesc;
	private String tooltip;
	private String iconfile;
	private ComponentResolverInterface resolver;

	private List<ComponentAttribute> attributes;
	private Map<String, ComponentAttribute> attributesMap;

	private ComponentPlugin(Node componentNode) throws Exception {
		this.id = XMLHandler.getTagAttribute(componentNode, "id");
		this.description = PropertyUtils.getCodedTranslation(XmlUtils
				.getTagOrAttribute(componentNode, "description"));
		this.iconfile = XmlUtils.getTagOrAttribute(componentNode, "iconfile");
		this.tooltip = PropertyUtils.getCodedTranslation(XmlUtils
				.getTagOrAttribute(componentNode, "tooltip"));
		this.category = XmlUtils.getTagOrAttribute(componentNode, "category");
		this.categorydesc = PropertyUtils
				.getCodedTranslation("i18n:com.yonyou.bq8.di.component.resolvers:Component.Category."
						+ this.category);
		this.classname = XmlUtils.getTagOrAttribute(componentNode, "classname");
		this.resolver = (ComponentResolverInterface) ReflectionUtils
				.newInstance(this.classname);

		// 属性
		Node attributesNode = XMLHandler
				.getSubNode(componentNode, "attributes");
		if (attributesNode != null) {
			List<Node> attributeNodes = XMLHandler.getNodes(attributesNode,
					"attribute");
			for (Node attrNode : attributeNodes) {
				addAttribute(ComponentAttribute.instance(attrNode));
			}
		}
	}

	public void addAttribute(ComponentAttribute attr) {
		if (attributes == null) {
			attributes = new ArrayList<ComponentAttribute>();
		}
		attributes.add(attr);

		if (attributesMap == null) {
			attributesMap = new HashMap<String, ComponentAttribute>();
		}
		attributesMap.put(attr.getName(), attr);
	}

	public ComponentAttribute getAttribute(String name) {
		if (attributesMap != null) {
			return attributesMap.get(name);
		}
		return null;
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

}
