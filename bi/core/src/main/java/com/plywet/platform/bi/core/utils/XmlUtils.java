package com.plywet.platform.bi.core.utils;

import javax.xml.transform.TransformerException;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtils {

	/**
	 * 通过ID查找子Node
	 * 
	 * @param n
	 * @param id
	 * @return
	 */
	public static final Node getSubNodeById(Node n, String id) {
		int i;
		NodeList children;
		Node childnode;

		if (n == null) {
			return null;
		}

		children = n.getChildNodes();

		for (i = 0; i < children.getLength(); i++) {
			childnode = children.item(i);
			NamedNodeMap nnm = childnode.getAttributes();
			if (nnm != null) {
				Node attr = nnm.getNamedItem("id");
				if (attr != null && id.equals(attr.getNodeValue())) {
					return childnode;
				}
			}

			childnode = getSubNodeById(childnode, id);
			if (childnode != null) {
				return childnode;
			}
		}
		return null;
	}

	public static String getTagOrAttribute(Node pluginNode, String tag) {
		String string = XMLHandler.getTagValue(pluginNode, tag);
		if (string == null) {
			string = XMLHandler.getTagAttribute(pluginNode, tag);
		}
		return string;
	}

	/**
	 * 设置属性
	 * 
	 * @param node
	 * @param name
	 * @param value
	 */
	public static void setAttribute(Node node, String name, String value) {
		if (node == null) {
			return;
		}
		if (node instanceof Element) {
			((Element) node).setAttribute(name, value);
		} else if (node instanceof Document) {
			Attr attr = ((Document) node).createAttribute(name);
			attr.setValue(value);
		}

	}

	/**
	 * 返回文档Document的XML格式的字符串
	 * 
	 * @param node
	 * @return
	 */
	public static String toXMLString(Node node) throws TransformerException {
		String str = "";

		String nodeName = node.getNodeName();

		if (nodeName.equals("#text")) {
			return Const.NVL(node.getNodeValue(), "");
		}

		if (!nodeName.equals("#document")) {
			str = str + "<" + nodeName;
			NamedNodeMap nnm = node.getAttributes();
			if (nnm != null) {
				for (int i = 0; i < nnm.getLength(); i++) {
					Node attr = nnm.item(i);
					str = str + " " + attr.getNodeName() + "=\""
							+ attr.getNodeValue() + "\"";
				}
			}
			str = str + ">";
			str = str + Const.CR;
		}

		NodeList nodeList = node.getChildNodes();
		if (nodeList != null) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node subNode = nodeList.item(i);
				str = str + toXMLString(subNode);
			}
		}

		if (!nodeName.equals("#document")) {
			str = str + "</" + nodeName + ">";
			str = str + Const.CR;
		}
		return str;

	}
}
