package com.flywet.platform.bi.component.resolvers.input;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.pentaho.pms.util.Const;
import org.w3c.dom.Node;

import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.core.utils.JSONUtils;

public class FLYComboBoxResolver extends FLYSelectMenuResolver implements
		ComponentResolverInterface {

	@SuppressWarnings("unchecked")
	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_SELECT);

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

			HTML.writeAttributes(node.getAttributes(), new String[] {
					HTML.ATTR_ID, HTML.ATTR_STATE, HTML.ATTR_ON_CHANGE,
					HTML.ATTR_DISABLED, ATTR_INTERACTION }, html, attrs);
			if (styleClass != null) {
				html.writeAttribute(HTML.ATTR_CLASS, styleClass);
			}

			HTML.writeStyleAttribute(node, html, attrs);

			// options
			String value = HTML.getTagAttribute(node, HTML.ATTR_VALUE, attrs);
			getOptions(node, html, script, attrs, value);

			html.endElement(HTML.COMPONENT_TYPE_BASE_SELECT);

			Map<String, Object> map = HTML.getAttributesMap(node
					.getAttributes(), new String[] { HTML.ATTR_ID,
					HTML.ATTR_NAME }, attrs);
			JSONObject jo = JSONUtils.convertToJSONObject(map);
			// id
			jo.put(HTML.ATTR_ID, id);

			// onchange
			String onchange = HTML.getTagAttribute(node, HTML.ATTR_ON_CHANGE,
					attrs);
			String interaction = HTML.getTagAttribute(node, ATTR_INTERACTION,
					attrs);
			if (onchange != null || interaction != null) {
				onchange = (interaction != null) ? "Flywet.interaction.action(this.value,"
						+ interaction + ");" + Const.NVL(onchange, "")
						: Const.NVL(onchange, "");
				jo.put("onChange", onchange);
			}

			String weightVar = HTML.getTagAttribute(node, HTML.TAG_WEIGHT_VAR,
					attrs);
			script.add("Flywet.cw('ComboBox','" + Const.NVL(weightVar, "")
					+ "'," + jo.toJSONString() + ");");
		} catch (Exception e) {
			throw new BIPageException("ComboBox解析出现错误。");
		}
	}

	@Override
	public void renderScript(Node node, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
	}

}
