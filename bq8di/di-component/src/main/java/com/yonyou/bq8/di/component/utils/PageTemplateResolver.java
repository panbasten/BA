package com.yonyou.bq8.di.component.utils;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.yonyou.bq8.di.core.exception.DIPageException;

/**
 * 页面模板解析
 * 
 * @author PeterPan
 * 
 */
public class PageTemplateResolver {

	public static void resolver(Node node, HTMLWriter html,
			List<String> script, BQVariableResolver attrs, String fileUrl)
			throws DIPageException {
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
