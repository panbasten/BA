package com.yonyou.bq8.di.component.resolvers.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yonyou.bq8.di.component.components.tab.BQTabViewMeta;
import com.yonyou.bq8.di.component.components.tab.TabNode;
import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.resolvers.BaseComponentResolver;
import com.yonyou.bq8.di.component.resolvers.base.ForEachResolver;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.HTMLWriter;
import com.yonyou.bq8.di.core.exception.DIPageException;

public class BQTabViewResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public void renderScript(BQTabViewMeta tabView, List<String> script) {
		// 添加子页面脚本
		for (TabNode tab : tabView.getTabs()) {
			script.addAll(tab.getContentScript());
		}
		
		// 添加TabView脚本
		script.add("YonYou.cw(\"EasyTabs\",\"" + tabView.getId()
				+ "_var\",{id : \"" + tabView.getId() + "\"});");
	}

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
		String id = HTML.getTagAttribute(node, HTML.ATTR_ID, attrs);
		html.writeAttribute(HTML.ATTR_ID, id);

		if (HTML.containsTagName(node, HTML.ATTR_STYLE)) {
			html.writeAttribute(HTML.ATTR_STYLE, HTML.getTagAttribute(node,
					HTML.ATTR_STYLE, attrs));
		}

		// TODO 更换各种方向
		String styleClass = "ui-tab-container";
		if (HTML.containsTagName(node, HTML.ATTR_CLASS)) {
			styleClass = styleClass + " "
					+ HTML.getTagAttribute(node, HTML.ATTR_CLASS, attrs);
		}
		html.writeAttribute(HTML.ATTR_CLASS, styleClass);

		BQTabViewMeta tabView = new BQTabViewMeta();
		tabView.setId(id);
		getTabs(tabView, node, attrs, fileUrl);

		renderTitle(tabView, html);

		renderContent(tabView, html);

		renderScript(tabView, script);

		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
	}

	private void getTabs(BQTabViewMeta tabView, Node node,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		// 子节点类型，一种是tab，一种是foreach
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			String subNodeName = subNode.getNodeName();
			if (HTML.COMPONENT_TYPE_FOR_EACH.equalsIgnoreCase(subNodeName)) {
				String varStr = HTML.getTagAttribute(subNode,
						ForEachResolver.ATTRIBUTE_VAR, attrs);
				String indexVarStr = HTML.getTagAttribute(subNode,
						ForEachResolver.ATTRIBUTE_INDEX_VAR, attrs);
				String sizeVarStr = HTML.getTagAttribute(subNode,
						ForEachResolver.ATTRIBUTE_SIZE_VAR, attrs);
				Collection<?> items = (Collection<?>) HTML
						.getTagAttributeObject(subNode,
								ForEachResolver.ATTRIBUTE_ITEMS, attrs);
				if (items != null && items.size() > 0) {
					if (sizeVarStr != null) {
						attrs.addVariable(sizeVarStr, items.size());
					}
					int index = 0;
					for (Iterator<?> itet = items.iterator(); itet.hasNext();) {
						attrs.addVariable(varStr, itet.next());
						if (indexVarStr != null) {
							attrs.addVariable(indexVarStr, index);
						}
						index++;
						getTabs(tabView, subNode, attrs, fileUrl);
					}
				}
			} else if (HTML.COMPONENT_TYPE_TAB.equalsIgnoreCase(subNodeName)) {
				TabNode tab = new TabNode();
				String tabId = HTML.getTagAttribute(subNode, HTML.ATTR_ID,
						attrs);
				String tabTitle = HTML.getTagAttribute(subNode,
						HTML.ATTR_TITLE, attrs);
				tab.setId(tabId);
				tab.setTitle(tabTitle);

				HTMLWriter contentHtml = HTMLWriter.instance();
				List<String> contentScript = new ArrayList<String>();

				super.renderSub(subNode, contentHtml, contentScript, attrs,
						fileUrl);
				tab.setContentHtml(contentHtml);
				tab.setContentScript(contentScript);

				tabView.addTab(tab);
			}
		}
	}

	public void renderTitle(BQTabViewMeta tabView, HTMLWriter html)
			throws DIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_UL);
		html.writeAttribute(HTML.ATTR_ID, tabView.getId() + "_ul");
		html.writeAttribute(HTML.ATTR_CLASS, "ui-tabs");
		for (TabNode tab : tabView.getTabs()) {
			html.startElement(HTML.COMPONENT_TYPE_BASE_LI);
			html.startElement(HTML.COMPONENT_TYPE_BASE_A);
			html.writeAttribute(HTML.ATTR_HREF, "#" + tab.getId());
			html.writeText(tab.getTitle());
			html.endElement(HTML.COMPONENT_TYPE_BASE_A);
			html.endElement(HTML.COMPONENT_TYPE_BASE_LI);
		}
		html.endElement(HTML.COMPONENT_TYPE_BASE_UL);
	}

	public void renderContent(BQTabViewMeta tabView, HTMLWriter html) {
		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
		html.writeAttribute(HTML.ATTR_ID, tabView.getId() + "_container");
		html.writeAttribute(HTML.ATTR_CLASS, "ui-tab-panel-container");
		for (TabNode tab : tabView.getTabs()) {
			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
			html.writeAttribute(HTML.ATTR_ID, tab.getId());
			html.writeText(tab.getContentHtml().toString());
			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
		}
		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
	}

}
