package com.plywet.platform.bi.component.resolvers.container;

import java.util.List;

import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;
import com.plywet.platform.bi.core.utils.Utils;

public class FLYFieldSetResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String FIELDSET_STYLE_CLASS = "ui-fieldset";
	public static final String FIELDSET_CONTENT_STYLE_CLASS = "ui-fieldset-content";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_FIELDSET);

		HTML.writeStyleAttribute(node, html, attrs);

		String disabled = HTML.getTagAttribute(node, HTML.ATTR_DISABLED, attrs);
		String styleClass = FIELDSET_STYLE_CLASS;
		if (Boolean.parseBoolean(disabled)) {
			styleClass = HTML.STATE_DISABLED_CLASS + " " + styleClass;
		}
		HTML.writeStyleClassAttribute(node, html, attrs, styleClass);

		HTML.writeAttributes(node.getAttributes(), html, attrs);

		html.startElement(HTML.COMPONENT_TYPE_BASE_LEGEND);
		html.writeText(Const.NVL(HTML.getTagAttribute(node, HTML.ATTR_TITLE,
				attrs), ""));
		html.endElement(HTML.COMPONENT_TYPE_BASE_LEGEND);

		html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
		String id = HTML.getTagAttribute(node, HTML.ATTR_ID, attrs);
		if (!Utils.isEmpty(id)) {
			html.writeAttribute(HTML.ATTR_ID, id + ":content");
		}
		html.writeAttribute(HTML.ATTR_CLASS, FIELDSET_CONTENT_STYLE_CLASS);
		super.renderSub(node, html, script, attrs, fileUrl);
		html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);

		html.endElement(HTML.COMPONENT_TYPE_BASE_FIELDSET);
	}
}
