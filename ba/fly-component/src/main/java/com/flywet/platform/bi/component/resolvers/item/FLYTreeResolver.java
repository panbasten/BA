package com.flywet.platform.bi.component.resolvers.item;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.pentaho.di.core.Const;
import org.w3c.dom.Node;

import com.flywet.platform.bi.component.components.tree.FLYTreeMeta;
import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.resolvers.BaseComponentResolver;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.core.utils.JSONUtils;

public class FLYTreeResolver extends BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTR_CHECKBOX = "checkbox";
	public static final String ATTR_DND = "dnd";
	public static final String ATTR_DATA = "data";

	@SuppressWarnings("unchecked")
	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		try {
			html.startElement(HTML.COMPONENT_TYPE_BASE_DIV);
			String weightVar = HTML.getTagAttribute(node, HTML.TAG_WEIGHT_VAR,
					attrs);
			String id = HTML.getId(node, attrs);
			html.writeAttribute(HTML.ATTR_ID, id);

			HTML.writeStyleAttribute(node, html, attrs);
			HTML.writeStyleClassAttribute(node, html, attrs, "");

			html.endElement(HTML.COMPONENT_TYPE_BASE_DIV);

			Map<String, Object> map = HTML.getAttributesMap(node
					.getAttributes(), new String[] { ATTR_CHECKBOX, ATTR_DND,
					ATTR_DATA }, attrs);
			JSONObject jo = JSONUtils.convertToJSONObject(map);
			// id
			jo.put(HTML.ATTR_ID, id);

			// checkbox
			String checkbox = HTML.getTagAttribute(node, ATTR_CHECKBOX, attrs);
			if (!Const.isEmpty(checkbox))
				jo.put(ATTR_CHECKBOX, Boolean.valueOf(checkbox));

			// dnd
			String dnd = HTML.getTagAttribute(node, ATTR_DND, attrs);
			if (!Const.isEmpty(dnd))
				jo.put(ATTR_DND, Boolean.valueOf(dnd));

			// data
			FLYTreeMeta data = (FLYTreeMeta) HTML.getTagAttributeObject(node,
					ATTR_DATA, attrs);
			if (data != null) {
				jo.put(ATTR_DATA, data.getDataFormJa());
			}

			script.add("Flywet.cw('EasyTree', '" + Const.NVL(weightVar, "") + "',"
					+ jo.toJSONString() + ");");

		} catch (BIPageException e) {
			throw e;
		} catch (Exception e) {
			throw new BIPageException("解析树形出现错误", e);
		}
	}
}
