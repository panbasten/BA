package com.yonyou.bq8.di.component.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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
import com.yonyou.bq8.di.core.utils.XmlUtils;

public class PageTemplateResolverType {

	public static final String XML_FILE_HB_COMPONENTS = "hb-components.xml";

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

				Document document = XMLHandler.loadXMLFile(BQFileUtils
						.getInputStream(XML_FILE_HB_COMPONENTS,
								PageTemplateResolverType.class), null, true,
						false);

				Node componentsNode = XMLHandler.getSubNode(document,
						"components");
				List<Node> componentNodes = XMLHandler.getNodes(componentsNode,
						"component");
				for (Node componentNode : componentNodes) {
					String id = XMLHandler.getTagAttribute(componentNode, "id");
					ComponentPlugin p = ComponentPlugin.instance(componentNode);
					plugins.put(id.toLowerCase(), p);
					if (!categories.containsKey(p.getCategory())) {
						categories.put(p.getCategory(),
								new ArrayList<ComponentPlugin>());
						categoryNames.add(p.getCategorydesc());
					}
					categories.get(p.getCategory()).add(p);
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

class ComponentPlugin {
	private String id;
	private String description;
	private String classname;
	private String category;
	private String categorydesc;
	private String tooltip;
	private String iconfile;
	private ComponentResolverInterface resolver;

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
