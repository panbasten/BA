package com.plywet.platform.bi.component.resolvers.base;

import java.util.List;

import org.w3c.dom.Node;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;

public class HTMLComponentResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	@Override
	public void renderEnd(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) {
		html.endElement(node.getNodeName());
	}

	@Override
	public void renderStart(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		html.startElement(node.getNodeName());
		HTML.getAttributesString(node.getAttributes(), html, attrs);
	}

}
