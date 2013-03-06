package com.yonyou.bq8.di.component.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.resolvers.base.HTMLComponentResolver;
import com.yonyou.bq8.di.core.exception.DIPageException;
import com.yonyou.bq8.di.core.utils.BQFileUtils;
import com.yonyou.bq8.di.core.utils.PropertyUtils;
import com.yonyou.bq8.di.core.utils.ReflectionUtils;
import com.yonyou.bq8.di.core.utils.Utils;
import com.yonyou.bq8.di.core.utils.XmlUtils;

public class PageTemplateResolverType {

	public static final String XML_FILE_HB_COMPONENTS = "entities";

	private static final ComponentResolverInterface htmlResolver = new HTMLComponentResolver();

	private static Map<String, ComponentPlugin> plugins;

	private static Map<String, List<ComponentPlugin>> categories;

	private static List<String> categoryNames;

	private static AtomicBoolean init = new AtomicBoolean(false);

	public static void init() throws DIPageException {
		try {
			if (!init.get()) {
				plugins = new HashMap<String, ComponentPlugin>();
				categories = new HashMap<String, List<ComponentPlugin>>();
				categoryNames = new ArrayList<String>();

				File componentDir = BQFileUtils.getFile(XML_FILE_HB_COMPONENTS,
						PageTemplateResolverType.class);
				if (BQFileUtils.isDirectory(componentDir)) {
					File[] components = componentDir.listFiles();
					for (File com : components) {
						Document document = XMLHandler.loadXMLFile(com);

						Node componentNode = XMLHandler.getSubNode(document,
								"component");
						String id = XMLHandler.getTagAttribute(componentNode,
								"id");
						ComponentPlugin p = ComponentPlugin
								.instance(componentNode);
						plugins.put(id.toLowerCase(), p);
						if (!categories.containsKey(p.getCategory())) {
							categories.put(p.getCategory(),
									new ArrayList<ComponentPlugin>());
							categoryNames.add(p.getCategorydesc());
						}
						categories.get(p.getCategory()).add(p);
					}
				}

				init.set(true);
			}
		} catch (Exception e) {
			throw new DIPageException("无法读取组件XML配置文件: "
					+ XML_FILE_HB_COMPONENTS, e);
		}
	}

	public static Map<String, ComponentPlugin> getPlugins()
			throws DIPageException {
		init();
		return plugins;
	}

	public static Map<String, List<ComponentPlugin>> getCategories()
			throws DIPageException {
		init();
		return categories;
	}

	public static List<String> getCategoryNames() throws DIPageException {
		init();
		return categoryNames;
	}

	public static void replaceAll(Node node, HTMLWriter html,
			List<String> script, BQVariableResolver attrs, String fileUrl)
			throws DIPageException {

		String nodeName = node.getNodeName();
		if (nodeName.startsWith(HTML.COMPONENT_TYPE_BQ_PREFIX)) {
			if (getPlugins().containsKey(nodeName.toLowerCase())) {
				getPlugins().get(nodeName.toLowerCase()).getResolver().render(
						node, html, script, attrs, fileUrl);
				return;
			}
		} else if (nodeName.equals("#text")) {
			html.writeText(PageTemplateInterpolator.interpolateExpressions(
					Const.removeTAB(Const.removeCRLF(node.getNodeValue())),
					attrs));
			return;
		}
		htmlResolver.render(node, html, script, attrs, fileUrl);

	}

}

class ComponentAttribute {
	private String name;
	private boolean required;
	private Class<?> type;
	private String defaultValueStr;
	private Object defaultValue;
	private String description;

	private ComponentAttribute(Node attributesNode) throws Exception {
		this.name = XMLHandler.getTagAttribute(attributesNode, "name");
		this.required = Utils.toBoolean(XMLHandler.getTagAttribute(
				attributesNode, "required"), false);

		String typeStr = Utils.NVL(XMLHandler.getTagAttribute(attributesNode,
				"type"), "java.lang.String");
		this.type = Class.forName(typeStr);

		this.defaultValueStr = XMLHandler.getTagAttribute(attributesNode,
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
	}

	public static ComponentAttribute instance(Node attributesNode)
			throws Exception {
		return new ComponentAttribute(attributesNode);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

class ComponentPlugin {
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
