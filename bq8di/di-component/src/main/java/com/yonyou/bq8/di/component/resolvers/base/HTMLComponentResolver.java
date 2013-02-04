package com.yonyou.bq8.di.component.resolvers.base;

import java.util.List;

import org.w3c.dom.Node;

import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.resolvers.BaseComponentResolver;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.HTMLWriter;
import com.yonyou.bq8.di.core.exception.DIPageException;

public class HTMLComponentResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	@Override
	public void renderEnd(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) {
		html.endElement(node.getNodeName());
	}

	@Override
	public void renderStart(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		html.startElement(node.getNodeName());
		HTML.getAttributesString(node.getAttributes(), html, attrs);
	}

}
