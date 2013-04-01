package com.plywet.platform.bi.component.resolvers.button;

import java.util.List;

import org.w3c.dom.Node;

import com.plywet.platform.bi.component.components.button.FLYButtonMeta;
import com.plywet.platform.bi.component.core.ComponentResolverInterface;
import com.plywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.plywet.platform.bi.component.utils.FLYVariableResolver;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.component.utils.HTMLWriter;
import com.plywet.platform.bi.core.exception.BIPageException;
import com.plywet.platform.bi.core.utils.Utils;

public class FLYPushButtonResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		String type = HTML.getTagAttribute(node, HTML.ATTR_TYPE, attrs);
		if (FLYButtonMeta.BUTTON_TYPE_SEPARATOR.equalsIgnoreCase(type)) {
			renderSeparatorType(node, html, script, attrs);
		} else {
			renderButtonType(node, html, script, attrs);
		}
	}

	private void renderSeparatorType(Node node, HTMLWriter html,
			List<String> script, FLYVariableResolver attrs) {
		html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);

		HTML.writeStyleClassAttribute(node, html, attrs, HTML.SEPARATOR_CLASS);
		HTML.writeStyleAttribute(node, html, attrs);

		if (HTML.containsTagName(node, HTML.ATTR_ID))
			html.writeAttribute(HTML.ATTR_ID, HTML.getTagAttribute(node,
					HTML.ATTR_ID, attrs));

		html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
		html.writeAttribute(HTML.ATTR_CLASS, HTML.SEPARATOR_ICON_CLASS);

		html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
		html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
	}

	private String resolveStyleClass(Node node, FLYVariableResolver attrs) {
		String icon = HTML.getTagAttribute(node, HTML.ATTR_ICON, attrs);
		String label = HTML.getTagAttribute(node, HTML.ATTR_LABEL, attrs);
		String state = HTML.getTagAttribute(node, HTML.ATTR_STATE, attrs);
		String userClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS, attrs);
		String styleClass = "";

		if (label != null && icon == null) {
			styleClass = HTML.BUTTON_TEXT_ONLY_BUTTON_CLASS;
		} else if (label != null && icon != null) {
			styleClass = "right".equalsIgnoreCase(HTML.getTagAttribute(node,
					HTML.ATTR_ICON_POS, attrs)) ? HTML.BUTTON_TEXT_ICON_RIGHT_BUTTON_CLASS
					: HTML.BUTTON_TEXT_ICON_LEFT_BUTTON_CLASS;
		} else if (label == null && icon != null) {
			styleClass = HTML.BUTTON_ICON_ONLY_BUTTON_CLASS;
		}

		if (HTML.ATTR_STATE_DISABLED.equalsIgnoreCase(state)) {
			styleClass = styleClass + " " + "ui-state-disabled";
		} else if (HTML.ATTR_STATE_ACTIVE.equalsIgnoreCase(state)) {
			styleClass = styleClass + " " + "ui-state-active";
		}

		if (userClass != null) {
			styleClass = styleClass + " " + userClass;
		}

		return styleClass;

	}

	private void renderButtonType(Node node, HTMLWriter html,
			List<String> script, FLYVariableResolver attrs)
			throws BIPageException {
		html.startElement(HTML.COMPONENT_TYPE_BASE_BUTTON);
		html.writeAttribute(HTML.ATTR_TYPE, FLYButtonMeta.BUTTON_TYPE_BUTTON);
		html.writeAttribute(HTML.ATTR_CLASS, resolveStyleClass(node, attrs));

		// mouseOver
		String mouseOverEvent = "$(this).addClass('ui-state-hover');"
				+ Utils.NVL(HTML.getTagAttribute(node, HTML.ATTR_ON_MOUSE_OVER,
						attrs), "");
		html.writeAttribute(HTML.ATTR_ON_MOUSE_OVER, mouseOverEvent);

		// mouseOut
		String mouseOutEvent = "$(this).removeClass('ui-state-hover');"
				+ Utils.NVL(HTML.getTagAttribute(node, HTML.ATTR_ON_MOUSE_OUT,
						attrs), "");
		html.writeAttribute(HTML.ATTR_ON_MOUSE_OUT, mouseOutEvent);

		HTML.writeAttributes(node.getAttributes(), new String[] {
				HTML.ATTR_TYPE, HTML.ATTR_ON_MOUSE_OVER,
				HTML.ATTR_ON_MOUSE_OUT, HTML.ATTR_STATE, HTML.ATTR_ICON,
				HTML.ATTR_ICON_POS, HTML.ATTR_LABEL }, html, attrs);

		if (HTML.ATTR_STATE_DISABLED.equalsIgnoreCase(HTML.getTagAttribute(
				node, HTML.ATTR_STATE, attrs))) {
			html.writeAttribute(HTML.ATTR_DISABLED, HTML.ATTR_DISABLED);
		}

		// icon
		String icon = HTML.getTagAttribute(node, HTML.ATTR_ICON, attrs);
		if (icon != null) {
			String defaultIconClass = "right".equalsIgnoreCase(HTML
					.getTagAttribute(node, HTML.ATTR_ICON_POS, attrs)) ? HTML.BUTTON_RIGHT_ICON_CLASS
					: HTML.BUTTON_LEFT_ICON_CLASS;
			String iconClass = defaultIconClass + " " + icon;

			html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			html.writeAttribute(HTML.ATTR_CLASS, iconClass);
			html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
		}

		// text
		html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
		html.writeAttribute(HTML.ATTR_CLASS, HTML.BUTTON_TEXT_CLASS);

		String label = HTML.getTagAttribute(node, HTML.ATTR_LABEL, attrs);
		if (label != null) {
			html.writeText(label);
		} else {
			html.writeText("ui-text");
		}
		html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);

		html.endElement(HTML.COMPONENT_TYPE_BASE_BUTTON);
	}

}
