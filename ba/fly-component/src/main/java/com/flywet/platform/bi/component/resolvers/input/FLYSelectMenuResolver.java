package com.flywet.platform.bi.component.resolvers.input;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.core.exception.BIPageException;

public class FLYSelectMenuResolver extends BaseComponentResolver implements
		ComponentResolverInterface {
	public static final String ATTR_REQUIRED = "required";
	public static final String ATTR_INTERACTION = "interaction";

	public static final String ATTRIBUTE_ITEMS = "items";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_SELECT);

			String state = HTML.getTagAttribute(node, HTML.ATTR_STATE, attrs);
			String disabled = HTML.getTagAttribute(node, HTML.ATTR_DISABLED,
					attrs);

			String styleClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS,
					attrs);

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
				onchange = (interaction != null) ? "Flywet.interaction.action(this.value,"
						+ interaction + ");" + Const.NVL(onchange, "")
						: Const.NVL(onchange, "");
				html.writeAttribute(HTML.ATTR_ON_CHANGE, onchange);
			}

			HTML.writeAttributes(node.getAttributes(), new String[] {
					HTML.ATTR_STATE, HTML.ATTR_ON_CHANGE, HTML.ATTR_DISABLED,
					ATTR_INTERACTION }, html, attrs);
			if (styleClass != null) {
				html.writeAttribute(HTML.ATTR_CLASS, styleClass);
			}

			HTML.writeStyleAttribute(node, html, attrs);

			// options
			String value = HTML.getTagAttribute(node, HTML.ATTR_VALUE, attrs);
			getOptions(node, html, script, attrs, value);

			html.endElement(HTML.COMPONENT_TYPE_BASE_SELECT);

			// if (isRequired(node, attrs)) {
			// html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			// html.writeAttribute(HTML.ATTR_CLASS, HTML.REQUIRED_CLASS);
			// html.writeText("*");
			// html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			// }
		} catch (Exception e) {
			throw new BIPageException("SelectMenu解析出现错误。");
		}
	}

	protected void getOptions(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String val) {
		// 子节点类型，一种是option，一种是options
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			String subNodeName = subNode.getNodeName();
			if (HTML.COMPONENT_TYPE_OPTION.equalsIgnoreCase(subNodeName)) {
				String value = HTML.getTagAttribute(subNode, HTML.ATTR_VALUE,
						attrs);
				String label = HTML.getTagAttribute(subNode, HTML.ATTR_LABEL,
						attrs);
				html.startElement(HTML.COMPONENT_TYPE_BASE_OPTION);
				html.writeAttribute(HTML.ATTR_VALUE, value);
				if (val != null && val.equals(value)) {
					html.writeAttribute(HTML.ATTR_SELECTED, "");
				}
				html.writeText(label);
				html.endElement(HTML.COMPONENT_TYPE_BASE_OPTION);
			} else if (HTML.COMPONENT_TYPE_OPTIONS
					.equalsIgnoreCase(subNodeName)) {
				Collection<?> items = (Collection<?>) HTML
						.getTagAttributeObject(subNode, ATTRIBUTE_ITEMS, attrs);
				String valueVar = HTML.getTagAttribute(subNode,
						HTML.ATTR_VALUE, attrs).trim();
				String labelVar = HTML.getTagAttribute(subNode,
						HTML.ATTR_LABEL, attrs).trim();
				if (items != null && items.size() > 0) {
					FLYVariableResolver optionsAttrs = new FLYVariableResolver();
					String value, label;
					for (Iterator<?> itet = items.iterator(); itet.hasNext();) {
						optionsAttrs.addVariable("option", itet.next());
						if (StringUtils.isNumeric(valueVar)
								&& StringUtils.isNumeric(labelVar)) {
							value = PageTemplateInterpolator
									.evaluate("${option[" + valueVar + "]}",
											optionsAttrs);
							label = PageTemplateInterpolator
									.evaluate("${option[" + labelVar + "]}",
											optionsAttrs);
						} else {
							value = PageTemplateInterpolator.evaluate(
									"${option." + valueVar + "}", optionsAttrs);
							label = PageTemplateInterpolator.evaluate(
									"${option." + labelVar + "}", optionsAttrs);
						}

						html.startElement(HTML.COMPONENT_TYPE_BASE_OPTION);
						html.writeAttribute(HTML.ATTR_VALUE, value);
						if (val != null && val.equals(value)) {
							html.writeAttribute(HTML.ATTR_SELECTED, "");
						}
						html.writeText(label);
						html.endElement(HTML.COMPONENT_TYPE_BASE_OPTION);
					}
				}
			}
		}
	}
}
