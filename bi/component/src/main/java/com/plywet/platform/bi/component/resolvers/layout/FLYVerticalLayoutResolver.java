package com.plywet.platform.bi.component.resolvers.layout;

import java.util.List;

import org.pentaho.di.core.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.component.utils.PageTemplateResolver;
import com.plywet.platform.bi.component.utils.PageTemplateResolverType;
import com.plywet.platform.bi.core.exception.BIPageException;
import com.plywet.platform.bi.core.utils.Utils;

public class FLYVerticalLayoutResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);

			HTML.writeStyleAttribute(node, html, attrs);
			HTML.writeStyleClassAttribute(node, html, attrs, HTML.LAYOUT_CLASS);

			HTML.writeAttributes(node.getAttributes(), html, attrs);

			renderItems(node, html, script, attrs, fileUrl);

			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
		} catch (Exception e) {
			throw new BIPageException("VerticalLayout解析出现错误。");
		}
	}

	private void renderItems(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		NodeList nodeList = node.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);

			String subNodeName = subNode.getNodeName();

			// 如果是文字，判断是否为空，如果为空则不单独起一行
			if (subNodeName.equals("#text")) {
				if (Utils.isEmpty(Const.removeTAB(Const.removeCRLF(subNode
						.getNodeValue())))) {
					continue;
				}
			}

			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
			html.writeAttribute(HTML.ATTR_CLASS, HTML.LAYOUT_SINGLE_CLASS);
			PageTemplateResolver.resolverNode(subNode, html, script, attrs,
					fileUrl);
			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
		}
	}
}
