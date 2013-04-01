package com.plywet.platform.bi.component.resolvers.display;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;

public class FLYVerticalLineResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {

		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);

		HTML.writeStyleClassAttribute(node, html, attrs, HTML.LAYOUT_CLASS);

		HTML.writeStyleAttribute(node, html, attrs);

		HTML.writeAttributes(node.getAttributes(), html, attrs);

		renderItems(node, html, script, attrs, fileUrl);

		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
	}

	private void renderItems(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		NodeList nodeList = node.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
			html.writeAttribute(HTML.ATTR_CLASS, HTML.LAYOUT_SINGLE_CLASS);

			super.renderSub(subNode, html, script, attrs, fileUrl);

			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
		}
	}

}
