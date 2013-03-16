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

public class FLYInputTextResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTR_VALIDATE = "validate";

	public static final String ATTR_REQUIRED = "required";

	public static final String ATTR_INTERACTION = "interaction";

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_INPUT);

			String state = HTML.getTagAttribute(node, HTML.ATTR_STATE, attrs);
			String disabled = HTML.getTagAttribute(node, HTML.ATTR_DISABLED,
					attrs);

			String validate = HTML.getTagAttribute(node, ATTR_VALIDATE, attrs);
			boolean required = false;
			if (validate != null) {
				int validateIndex = validate.indexOf(ATTR_REQUIRED);
				if (validateIndex > -1) {
					int validateIndex2 = validate.indexOf(",", validateIndex);
					if (validateIndex2 < validateIndex) {
						validateIndex2 = validate.length();
					}
					String subVal = validate.substring(validateIndex,
							validateIndex2);
					subVal = subVal.replaceAll("\"", "").replaceAll("'", "")
							.replace("}", "");
					String[] kv = subVal.split(":");
					required = Boolean.valueOf(kv[1].trim());
				}
			}

			String styleClass = HTML.getTagAttribute(node, HTML.ATTR_CLASS,
					attrs);

			if (HTML.ATTR_STATE_DISABLED.equals(state)
					|| Boolean.parseBoolean(disabled)) {
				html.writeAttribute(HTML.ATTR_STATE_DISABLED, "");
				styleClass = (styleClass == null) ? "ui-state-disabled"
						: styleClass + " ui-state-disabled";
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
				onchange = (interaction != null) ? "Plywet.interaction.action("
						+ val + "," + interaction + ");"
						+ Const.NVL(onchange, "") : Const.NVL(onchange, "");
				html.writeAttribute(HTML.ATTR_ON_CHANGE, onchange);
			}

			HTML.getAttributesString(node.getAttributes(), new String[] {
					HTML.ATTR_CLASS, HTML.ATTR_STATE, HTML.ATTR_ON_CHANGE,
					HTML.ATTR_DISABLED, ATTR_INTERACTION }, html, attrs);
			if (styleClass != null) {
				html.writeAttribute(HTML.ATTR_CLASS, styleClass);
			}

			html.endElement(HTML.COMPONENT_TYPE_BASE_INPUT);
			if (required) {
				html.startElement(HTML.COMPONENT_TYPE_BASE_SPAN);
				html.writeAttribute(HTML.ATTR_CLASS, HTML.REQUIRED_CLASS);
				html.writeText("*");
				html.endElement(HTML.COMPONENT_TYPE_BASE_SPAN);
			}
		} catch (Exception e) {
			throw new BIPageException("InputText解析出现错误。");
		}
	}
}
