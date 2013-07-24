package com.flywet.platform.bi.component.resolvers.layout;

import java.util.List;

import org.pentaho.di.core.Const;
import org.w3c.dom.Node;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;

public class FLYHorizontalLayoutResolver extends BaseComponentResolver
		implements ComponentResolverInterface {
	
	public static final String ATTR_ITEM_MARGIN = "itemMargin";

	private static final String HORIZONTAL_LAYOUT_CLASS = "ui-horizontal-layout "
			+ HTML.LAYOUT_CLASS;;

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);

			HTML.writeStyleAttribute(node, html, attrs);
			HTML.writeStyleClassAttribute(node, html, attrs,
					HORIZONTAL_LAYOUT_CLASS);

			HTML.writeAttributes(node.getAttributes(), html, attrs);

			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
			html.writeAttribute(HTML.ATTR_CLASS, HTML.LAYOUT_SINGLE_CLASS);

			String id = HTML.getTagAttribute(node, HTML.ATTR_ID, attrs);
			if (!Const.isEmpty(id)) {
				html.writeAttribute(HTML.ATTR_ID, id + ":Content");
			}

			super.renderSub(node, html, script, attrs, fileUrl);

			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);

			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
		} catch (BIPageException e) {
			throw e;
		} catch (Exception e) {
			throw new BIPageException("HorizontalLayout解析出现错误。");
		}
	}
}
