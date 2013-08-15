package com.flywet.platform.bi.component.resolvers.input;

import java.util.List;

import org.json.simple.JSONObject;
import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.Utils;

public class FLYInputTextResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTR_INTERACTION = "interaction";

	@SuppressWarnings("unchecked")
	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_INPUT);

			String id = HTML.getId(node, attrs);
			html.writeAttribute(HTML.ATTR_ID, id);
			html.writeAttribute(HTML.ATTR_NAME, id);

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
			String type = HTML.getTagAttribute(node, HTML.ATTR_TYPE, attrs);
			if (onchange != null || interaction != null) {
				String val;
				if (HTML.ATTR_INPUT_TYPE_CHECKBOX.equalsIgnoreCase(type)) {
					val = "this.checked";
				} else {
					val = "this.value";
				}
				onchange = (interaction != null) ? "Flywet.interaction.action("
						+ val + "," + interaction + ");"
						+ Const.NVL(onchange, "") : Const.NVL(onchange, "");
				html.writeAttribute(HTML.ATTR_ON_CHANGE, onchange);
			}

			HTML.writeAttributes(node.getAttributes(), new String[] {
					HTML.ATTR_STATE, HTML.ATTR_ON_CHANGE, HTML.ATTR_DISABLED,
					ATTR_INTERACTION, HTML.ATTR_TEXT }, html, attrs);
			if (styleClass != null) {
				html.writeAttribute(HTML.ATTR_CLASS, styleClass);
			}

			// 如果是checkbox设置值为"on"
			if (HTML.ATTR_INPUT_TYPE_CHECKBOX.equalsIgnoreCase(type)) {
				html.writeAttribute(HTML.ATTR_VALUE, "on");
				boolean val = Utils.toBoolean(HTML.getTagAttribute(node,
						HTML.ATTR_VALUE, attrs), false);
				if (val) {
					html.writeAttribute("checked", "checked");
				}
			}

			HTML.writeStyleAttribute(node, html, attrs, "float:left;");

			html.endElement(HTML.COMPONENT_TYPE_BASE_INPUT);

			if (HTML.ATTR_INPUT_TYPE_CHECKBOX.equalsIgnoreCase(type)) {
				String text = HTML.getTagAttribute(node, HTML.ATTR_TEXT, attrs);
				if (!Const.isEmpty(text)) {
					html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
					HTML.writeStyleAttribute(node, html, attrs,
							"float:left;margin-left:10px;");
					html.writeText(text);
					html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);
				}
			}
			// if (isRequired(node, attrs)) {
			// html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			// html.writeAttribute(HTML.ATTR_CLASS, HTML.REQUIRED_CLASS);
			// html.writeText("*");
			// html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			// }

			if (HTML.ATTR_INPUT_TYPE_TEXT.equalsIgnoreCase(type)
					|| HTML.ATTR_INPUT_TYPE_PASSWORD.equalsIgnoreCase(type)) {

				String validate = HTML.getTagAttribute(node,
						HTML.ATTR_VALIDATE, attrs);

				if (!Const.isEmpty(validate)) {
					JSONObject jo = JSONUtils.convertStringToJSONObject("{"
							+ validate + "}");
					jo.put(HTML.ATTR_ID, id);

					String weightVar = HTML.getTagAttribute(node,
							HTML.TAG_WEIGHT_VAR, attrs);
					script.add("Flywet.cw('ValidataBox','"
							+ Const.NVL(weightVar, "") + "',"
							+ jo.toJSONString() + ");");
				}
			}
		} catch (Exception e) {
			throw new BIPageException("InputText解析出现错误。");
		}
	}
}
