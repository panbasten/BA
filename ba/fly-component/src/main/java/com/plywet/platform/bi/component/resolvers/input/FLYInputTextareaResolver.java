package com.plywet.platform.bi.component.resolvers.input;

import java.util.List;

import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;

import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;

public class FLYInputTextareaResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	public static final String ATTR_INTERACTION = "interaction";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_TEXTAREA);

			String state = HTML.getTagAttribute(node, HTML.ATTR_STATE, attrs);
			String disabled = HTML.getTagAttribute(node, HTML.ATTR_DISABLED,
					attrs);

			String styleClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS,
					attrs);

			String val = HTML.getTagAttribute(node, HTML.ATTR_VALUE, attrs);

			if (HTML.ATTR_STATE_DISABLED.equals(state)
					|| Boolean.parseBoolean(disabled)) {
				html.writeAttribute(HTML.ATTR_STATE_DISABLED, "");
				styleClass = (styleClass == null) ? HTML.STATE_DISABLED_CLASS
						: styleClass + " " + HTML.STATE_DISABLED_CLASS;
			} else if (HTML.ATTR_STATE_READONLY.equals(state)) {
				html.writeAttribute(HTML.ATTR_STATE_READONLY, "");
			}

			// onchange
			String onchange = HTML.getTagAttribute(node, HTML.ATTR_ON_CHANGE,
					attrs);
			String interaction = HTML.getTagAttribute(node, ATTR_INTERACTION,
					attrs);
			if (onchange != null || interaction != null) {
				onchange = (interaction != null) ? "Plywet.interaction.action(this.value,"
						+ interaction + ");" + Const.NVL(onchange, "")
						: Const.NVL(onchange, "");
				html.writeAttribute(HTML.ATTR_ON_CHANGE, onchange);
			}

			HTML.writeAttributes(node.getAttributes(), new String[] {
					HTML.ATTR_STATE, HTML.ATTR_ON_CHANGE, HTML.ATTR_DISABLED,
					HTML.ATTR_VALUE, ATTR_INTERACTION }, html, attrs);
			if (styleClass != null) {
				html.writeAttribute(HTML.ATTR_CLASS, styleClass);
			}

			HTML.writeStyleAttribute(node, html, attrs);

			html.writeText(val);

			html.endElement(HTML.COMPONENT_TYPE_BASE_TEXTAREA);
			// if (isRequired(node, attrs)) {
			// html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			// html.writeAttribute(HTML.ATTR_CLASS, HTML.REQUIRED_CLASS);
			// html.writeText("*");
			// html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			// }
		} catch (Exception e) {
			throw new BIPageException("InputText解析出现错误。");
		}
	}
}
