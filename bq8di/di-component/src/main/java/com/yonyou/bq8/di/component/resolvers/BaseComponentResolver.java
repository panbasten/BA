package com.yonyou.bq8.di.component.resolvers;

import java.util.List;

import org.pentaho.di.core.Const;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yonyou.bq8.di.component.core.ComponentDataInterface;
import com.yonyou.bq8.di.component.core.ComponentResolverInterface;
import com.yonyou.bq8.di.component.utils.BQVariableResolver;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.component.utils.HTMLWriter;
import com.yonyou.bq8.di.component.utils.PageTemplateResolver;
import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.core.exception.DIPageException;
import com.yonyou.bq8.di.core.utils.JSONUtils;

public abstract class BaseComponentResolver implements
		ComponentResolverInterface {

	public static final String ATTR_SHOW = "show";

	@Override
	public void render(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		renderStart(node, html, script, attrs, fileUrl);
		renderSub(node, html, script, attrs, fileUrl);
		renderEnd(node, html, script, attrs, fileUrl);
		renderScript(node, script, attrs, fileUrl);
	}

	@Override
	public void renderSub(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
		NodeList nodeList = node.getChildNodes();

		if (nodeList != null) {
			PageTemplateResolver.resolver(node, html, script, attrs, fileUrl);
		}
		if (node.getNodeValue() != null) {
			html.writeText(Const.removeTAB(Const
					.removeCRLF(node.getNodeValue())));
		}
	}

	@Override
	public void renderScript(Node node, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {

	}

	@Override
	public void renderEnd(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
	}

	@Override
	public void renderStart(Node node, HTMLWriter html, List<String> script,
			BQVariableResolver attrs, String fileUrl) throws DIPageException {
	}

	public void renderShow(Node node, HTMLWriter html, BQVariableResolver attrs) {
		String show = HTML.getTagAttribute(node, ATTR_SHOW, attrs);
		if (show != null) {
			if (Boolean.valueOf(show)) {
				html.writeAttribute(HTML.ATTR_STYLE, "display:block;");
			} else {
				html.writeAttribute(HTML.ATTR_STYLE, "display:none;");
			}
		}
	}

	public void renderShowWithStyle(Node node, HTMLWriter html,
			BQVariableResolver attrs) {
		String show = HTML.getTagAttribute(node, ATTR_SHOW, attrs);
		String style = HTML.getTagAttribute(node, HTML.ATTR_STYLE, attrs);
		if (show != null) {
			if (Boolean.valueOf(show)) {
				style = (style != null) ? "display:block;" + style
						: "display:block;";
			} else {
				style = (style != null) ? "display:none;" + style
						: "display:none;";
			}
		}
		if (style != null) {
			html.writeAttribute(HTML.ATTR_STYLE, style);
		}
	}

	public String getShowStyle(Node node, HTMLWriter html,
			BQVariableResolver attrs) {
		String show = HTML.getTagAttribute(node, ATTR_SHOW, attrs);
		if (show != null) {
			if (Boolean.valueOf(show)) {
				return "display:block;";
			} else {
				return "display:none;";
			}
		}
		return "";
	}

	public Node getFirstSubNode(Node node, String nodeName) {
		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				String subNodeName = subNode.getNodeName();
				if (nodeName.equalsIgnoreCase(subNodeName)) {
					return subNode;
				}
			}
		}
		return null;
	}

	public Object getJSONAttribute(Object data) throws DIJSONException {
		if (data instanceof ComponentDataInterface) {
			return ((ComponentDataInterface) data).getFormDataJo();
		} else {
			return JSONUtils.convert(data);
		}
	}

}
