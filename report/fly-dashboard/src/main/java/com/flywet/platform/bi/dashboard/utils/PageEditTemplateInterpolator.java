package com.flywet.platform.bi.dashboard.utils;

import org.pentaho.di.core.xml.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flywet.platform.bi.base.model.TemplateMeta;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.utils.PageTemplateResolverType;
import com.flywet.platform.bi.component.vo.ComponentPlugin;
import com.flywet.platform.bi.core.exception.BIPageException;

public class PageEditTemplateInterpolator {
	/**
	 * 为编辑器获得dom
	 * 
	 * @param fileUrl
	 * @param id
	 *            报表
	 * @return
	 * @throws BIPageException
	 */
	public static Document getDomForEditor(String fileUrl, String prefix)
			throws BIPageException {
		Document dom = PageTemplateInterpolator.getDom(fileUrl);
		if (dom == null) {
			return null;
		}

		int idx = 0;
		idx = modifySubDomForEditor(dom, prefix, idx);

		XMLUtils.setAttribute(dom,
				TemplateMeta.TEMPLATE_ATTRIBUTE_EDITOR_INDEX, String
						.valueOf(idx));

		return dom;
	}

	/**
	 * 为编辑器获得dom
	 * 
	 * @param domString
	 * @param prefix
	 * @return
	 * @throws BIPageException
	 */
	public static Document getDomForEditorWithContent(String domString,
			String prefix) throws BIPageException {
		Document dom = PageTemplateInterpolator.getDomWithContent(domString);
		if (dom == null) {
			return null;
		}

		int idx = 0;
		idx = modifySubDomForEditor(dom, prefix, idx);

		XMLUtils.setAttribute(dom,
				TemplateMeta.TEMPLATE_ATTRIBUTE_EDITOR_INDEX, String
						.valueOf(idx));

		return dom;
	}

	private static int modifySubDomForEditor(Node node, String prefix, int idx) {
		if (node == null) {
			return idx;
		}

		XMLUtils.setAttribute(node, TemplateMeta.TEMPLATE_ATTRIBUTE_EDITOR_ID,
				prefix + "_" + (idx++));
		ComponentPlugin plugin = PageTemplateResolverType.getPlugin(node
				.getNodeName());
		if (plugin != null) {
			XMLUtils
					.setAttribute(node,
							TemplateMeta.TEMPLATE_ATTRIBUTE_EDITOR_TYPE, plugin
									.getId());
			XMLUtils.setAttribute(node,
					TemplateMeta.TEMPLATE_ATTRIBUTE_EDITOR_CATEGORY, plugin
							.getCategory());
		}

		NodeList nodeList = node.getChildNodes();

		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				if (!XMLUtils.isTextNode(subNode)) {
					idx = modifySubDomForEditor(subNode, prefix, idx++);
				}
			}
		}
		return idx;
	}
}
