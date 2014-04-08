package com.flywet.platform.dk.multi;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EigenbaseXmlUtil {

	public static final String getTagAttribute(Node node, String attribute) {
		if (node == null)
			return null;

		String retval = null;

		NamedNodeMap nnm = node.getAttributes();
		if (nnm != null) {
			Node attr = nnm.getNamedItem(attribute);
			if (attr != null) {
				retval = attr.getNodeValue();
			}
		}
		return retval;
	}

	public static final String getTagValue(Node n, String tag) {
		NodeList children;
		Node childnode;

		if (n == null)
			return null;

		children = n.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			childnode = children.item(i);
			if (childnode.getNodeName().equalsIgnoreCase(tag)) {
				if (childnode.getFirstChild() != null)
					return childnode.getFirstChild().getNodeValue();
			}
		}
		return null;
	}

	/**
	 * 获得标签值或者属性值，标签值优先。
	 * 
	 * @param node
	 * @param tag
	 * @return
	 */
	public static final String getTagOrAttribute(Node node, String tag) {
		if (node instanceof Document) {
			node = ((Document) node).getDocumentElement();
		}
		String string = getTagValue(node, tag);
		if (string == null) {
			string = getTagAttribute(node, tag);
		}
		return string;
	}

	/**
	 * 通过标签获得第一个子节点
	 * 
	 * @param n
	 * @param tag
	 * @return
	 */
	public static final Node getSubNode(Node n, String tag) {
		int i;
		NodeList children;
		Node childnode;

		if (n == null)
			return null;

		children = n.getChildNodes();
		for (i = 0; i < children.getLength(); i++) {
			childnode = children.item(i);
			if (childnode.getNodeName().equalsIgnoreCase(tag)) {
				return childnode;
			}
		}
		return null;
	}

	/**
	 * 通过标签获得所有子节点
	 * 
	 * @param n
	 * @param tag
	 * @return
	 */
	public static final List<Node> getSubNodes(Node n, String tag) {
		NodeList children;
		Node childnode;

		List<Node> nodes = new ArrayList<Node>();

		if (n == null)
			return nodes;

		children = n.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			childnode = children.item(i);
			if (childnode.getNodeName().equalsIgnoreCase(tag)) // <file>
			{
				nodes.add(childnode);
			}
		}
		return nodes;
	}

	/**
	 * 从URL加载XML
	 * 
	 * @param resource
	 * @return
	 * @throws Exception
	 */
	public static final Document loadXmlFile(URL resource) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputStream inputStream = resource.openStream();
		Document doc;
		try {
			doc = db.parse(inputStream);
		} finally {
			inputStream.close();
		}

		return doc;
	}

	/**
	 * 从文件路径加载XML
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static final Document loadXmlFile(String filePath) throws Exception {
		File resource = new File(filePath);
		return loadXmlFile(resource.toURI().toURL());
	}

}
