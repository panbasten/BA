package com.plywet.platform.bi.component.utils;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.plywet.platform.bi.core.exception.BIPageException;

/**
 * 页面模板解析
 * 
 * @author PeterPan
 * 
 */
public class PageTemplateResolver {

	public static void resolver(Node node, HTMLWriter html,
			List<String> script, FLYVariableResolver attrs, String fileUrl)
			throws BIPageException {
		NodeList nodeList = node.getChildNodes();

		if (nodeList == null)
			return;

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			PageTemplateResolverType.replaceAll(subNode, html, script, attrs,
					fileUrl);
		}
	}
}
