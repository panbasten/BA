package com.flywet.platform.bi.component.resolvers.display;

import java.util.List;

import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;

public class FLYLabelObjectResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String LABLE_STYLE_CLASS = "ui-label-default ui-helper-clearfix";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {

		html.startElement(HTML.COMPONENT_TYPE_BASE_LABEL);
		String buddyStr = HTML.getTagAttribute(node, HTML.ATTR_BUDDY, attrs);
		if (buddyStr != null) {
			html.writeAttribute(HTML.ATTR_FOR, buddyStr);
		}

		HTML.writeStyleAttribute(node, html, attrs);

		String disabled = HTML.getTagAttribute(node, HTML.ATTR_DISABLED, attrs);
		String styleClass = LABLE_STYLE_CLASS;
		if (Boolean.parseBoolean(disabled)) {
			styleClass = HTML.STATE_DISABLED_CLASS + " " + styleClass;
		}
		
		HTML.writeStyleClassAttribute(node, html, attrs, styleClass);

		HTML.writeAttributes(node.getAttributes(), html, attrs);

		html.writeText(Const.NVL(HTML.getTagAttribute(node, HTML.ATTR_TEXT,
				attrs), ""));
		html.endElement(HTML.COMPONENT_TYPE_BASE_LABEL);

	}

}
