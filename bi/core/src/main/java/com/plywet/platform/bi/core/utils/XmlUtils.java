package com.plywet.platform.bi.core.utils;

import org.pentaho.di.core.xml.XMLHandler;
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

	public static void setAttribute(Element node, String name, String value) {
		if (node == null) {
			return;
		}
		node.setAttribute(name, value);
	}

}
