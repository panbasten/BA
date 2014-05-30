package com.flywet.platform.bi.component.resolvers;

import java.util.List;

import org.pentaho.di.core.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flywet.platform.bi.component.core.ComponentDataInterface;
import com.flywet.platform.bi.component.core.ComponentResolverInterface;
import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.component.utils.HTMLWriter;
import com.flywet.platform.bi.component.utils.PageTemplateResolver;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.exception.BIPageException;
import com.flywet.platform.bi.core.utils.JSONUtils;

public abstract class BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTR_VALIDATE = "validate";
	public static final String ATTR_REQUIRED = "required";

	@Override
	public void render(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		renderStart(node, html, script, attrs, fileUrl);
		renderSub(node, html, script, attrs, fileUrl);
		renderEnd(node, html, script, attrs, fileUrl);
		renderScript(node, script, attrs, fileUrl);
	}

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
		NodeList nodeList = node.getChildNodes();

		if (nodeList != null) {
			PageTemplateResolver.resolverSubNode(node, html, script, attrs,
					fileUrl);
		}
		if (node.getNodeValue() != null) {
			html.writeText(Const.removeTAB(Const.removeCRLF(node.getNodeValue())));
		}
	}

	@Override
	public void renderScript(Node node, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {

	}

	@Override
	public void renderEnd(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
	}

	@Override
	public void renderStart(Node node, HTMLWriter html, List<String> script,
			FLYVariableResolver attrs, String fileUrl) throws BIPageException {
	}

	public boolean isRequired(Node node, FLYVariableResolver attrs)
			throws BIPageException {
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
		return required;
	}

	public Object getJSONAttribute(Object data) throws BIJSONException {
		if (data instanceof ComponentDataInterface) {
			return ((ComponentDataInterface) data).getFormDataJo();
		} else {
			return JSONUtils.convert(data);
		}
	}

}
