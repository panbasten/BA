package com.flywet.platform.bi.component.resolvers.button;

import java.util.List;

import org.w3c.dom.Node;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;

public class FLYPushButtonGroupResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	@Override
	public void renderStart(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);

		HTML.writeStyleAttribute(node, html, attrs);

		String disabled = HTML.getTagAttribute(node, HTML.ATTR_DISABLED, attrs);
		String styleClass = "btn-group";
		if (Boolean.parseBoolean(disabled)) {
			styleClass = HTML.STATE_DISABLED_CLASS + " " + styleClass;
		}
		HTML.writeStyleClassAttribute(node, html, attrs, styleClass);

		HTML.writeAttributes(node.getAttributes(), html, attrs);
	}

	@Override
	public void renderEnd(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
	}

}
