package com.flywet.platform.bi.component.utils;

import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flywet.platform.bi.core.exception.BIPageException;

/**
 * 页面模板解析
 * 
 * @author PeterPan
 * 
 */
public class PageTemplateResolver {

	/**
	 * 解析node的所有子节点
	 * 
	 * @param node
	 * @param html
	 * @param script
	 * @param attrs
	 * @param fileUrl
	 * @throws BIPageException
	 */
	public static void resolverSubNode(Node node, HTMLWriter html,
			List<String> script, FLYVariableResolver attrs, String fileUrl)
			throws BIPageException {
		NodeList nodeList = node.getChildNodes();

		if (nodeList == null)
			return;

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node subNode = nodeList.item(i);
			resolverNode(subNode, html, script, attrs, fileUrl);
		}
	}

	/**
	 * 解析node节点，包括自身
	 * 
	 * @param node
	 * @param html
	 * @param script
	 * @param attrs
	 * @param fileUrl
	 * @throws BIPageException
	 */
	public static void resolverNode(Node node, HTMLWriter html,
			List<String> script, FLYVariableResolver attrs, String fileUrl)
			throws BIPageException {
		PageTemplateResolverType.replaceAll(node, html, script, attrs, fileUrl);
	}
}
