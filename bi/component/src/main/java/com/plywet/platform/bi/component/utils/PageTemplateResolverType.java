package com.plywet.platform.bi.component.utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.base.HTMLComponentResolver;
import com.plywet.platform.bi.component.vo.ComponentPlugin;
import com.plywet.platform.bi.core.exception.BIPageException;
import com.plywet.platform.bi.core.utils.FileUtils;

public class PageTemplateResolverType {

	public static final String XML_FILE_FLY_COMPONENTS = "META-INF/fly-composition.xml";
	public static final String XML_FILE_FLY_COMPONENTS_ENTITY_PREFIX = "META-INF/entities/";

	private static final ComponentResolverInterface htmlResolver = new HTMLComponentResolver();

	private static Map<String, ComponentPlugin> plugins;

	private static Map<String, List<ComponentPlugin>> categories;

	private static List<String> categoryNames;
	private static List<String> categoryCodes;

	private static AtomicBoolean init = new AtomicBoolean(false);

	public static void init() throws BIPageException {
		try {
			if (!init.get()) {
				plugins = new HashMap<String, ComponentPlugin>();
				categories = new HashMap<String, List<ComponentPlugin>>();
				categoryNames = new ArrayList<String>();
				categoryCodes = new ArrayList<String>();

				Document document = getXMLDocument(XML_FILE_FLY_COMPONENTS);
				Node componentsNode = XMLHandler.getSubNode(document,
						"components");
				List<Node> componentNodes = XMLHandler.getNodes(componentsNode,
						"component");
				for (Node componentNode : componentNodes) {
					String componentFilename = componentNode.getTextContent();
					initComponent(getXMLDocument(XML_FILE_FLY_COMPONENTS_ENTITY_PREFIX
							+ componentFilename));
				}

				init.set(true);
			}
		} catch (Exception e) {
			throw new BIPageException("无法读取组件XML配置文件: "
					+ XML_FILE_FLY_COMPONENTS, e);
		}
	}

	private static void initComponent(Document document) throws Exception {

		Node componentNode = XMLHandler.getSubNode(document, "component");
		String id = XMLHandler.getTagAttribute(componentNode, "id");
		ComponentPlugin p = ComponentPlugin.instance(componentNode);
		plugins.put(id.toLowerCase(), p);
		if (!categories.containsKey(p.getCategory())) {
			categories.put(p.getCategory(), new ArrayList<ComponentPlugin>());
			categoryNames.add(p.getCategorydesc());
			categoryCodes.add(p.getCategory());
		}
		categories.get(p.getCategory()).add(p);
	}

	private static Document getXMLDocument(String filename)
			throws KettleXMLException, FileNotFoundException {
		return XMLHandler.loadXMLFile(FileUtils.getInputStream(filename,
				PageTemplateResolverType.class), null, true, false);
	}

	public static Map<String, ComponentPlugin> getPlugins()
			throws BIPageException {
		init();
		return plugins;
	}

	public static Map<String, List<ComponentPlugin>> getCategories()
			throws BIPageException {
		init();
		return categories;
	}

	public static List<String> getCategoryNames() throws BIPageException {
		init();
		return categoryNames;
	}
	
	public static List<String> getCategoryCodes() throws BIPageException {
		init();
		return categoryCodes;
	}

	public static void replaceAll(Node node, HTMLWriter html,
			List<String> script, FLYVariableResolver attrs, String fileUrl)
			throws BIPageException {

		String nodeName = node.getNodeName();
		if (nodeName.startsWith(HTML.COMPONENT_TYPE_FLY_PREFIX)) {
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
