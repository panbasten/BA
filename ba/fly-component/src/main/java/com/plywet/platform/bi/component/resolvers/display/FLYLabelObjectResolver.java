package com.plywet.platform.bi.component.resolvers.display;

import java.util.List;

import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;

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
		HTML.writeStyleClassAttribute(node, html, attrs, HTML.LAYOUT_CLASS);
		HTML.writeAttributes(node.getAttributes(), html, attrs);

		html.writeText(Const.NVL(HTML.getTagAttribute(node, HTML.ATTR_TEXT,
				attrs), ""));
		html.endElement(HTML.COMPONENT_TYPE_BASE_LABEL);

	}

}
