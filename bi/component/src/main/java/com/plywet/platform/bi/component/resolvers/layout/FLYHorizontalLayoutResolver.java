package com.plywet.platform.bi.component.resolvers.layout;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;

public class FLYHorizontalLayoutResolver extends BaseComponentResolver
		implements ComponentResolverInterface {

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
			String styleClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS,
					attrs);
			styleClass = (styleClass != null) ? HTML.LAYOUT_CLASS + " "
					+ styleClass : HTML.LAYOUT_CLASS;
			html.writeAttribute(HTML.ATTR_CLASS, styleClass);

			renderShowWithStyle(node, html, attrs);

			HTML.getAttributesString(node.getAttributes(), new String[] {
					HTML.ATTR_CLASS, HTML.ATTR_STYLE }, html, attrs);

			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
			html.writeAttribute(HTML.ATTR_CLASS, HTML.LAYOUT_SINGLE_CLASS);
			renderItems(node, html, script, attrs, fileUrl);
			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);

			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
		} catch (Exception e) {
			throw new BIPageException("HorizontalLayout解析出现错误。");
		}
	}

	private void renderItems(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		NodeList nodeList = node.getChildNodes();

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			super.renderSub(subNode, html, script, attrs, fileUrl);
		}
	}

}
