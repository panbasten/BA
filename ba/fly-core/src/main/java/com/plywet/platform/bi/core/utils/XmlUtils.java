package com.plywet.platform.bi.core.utils;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

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

	public static String getTagOrAttribute(Node node, String tag) {
		String string = XMLHandler.getTagValue(node, tag);
		if (string == null) {
			string = XMLHandler.getTagAttribute(node, tag);
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
	 * 查找节点，并返回第一个符合条件节点
	 * 
	 * @param node
	 * @param express
	 *            xPath表达式
	 * @return
	 */
	public static Node selectSingleNode(Node node, String express) {
		Node result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (Node) xpath.evaluate(express, node, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 查找节点，返回符合条件的节点集
	 * 
	 * @param node
	 * @param express
	 *            xPath表达式
	 * @return
	 */
	public static NodeList selectNodes(Node node, String express) {
		NodeList result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (NodeList) xpath.evaluate(express, node,
					XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 将源节点添加到目标节点内部，放置在其所有子节点的后面
	 * 
	 * @param source
	 * @param target
	 */
	public static void appendTo(Node source, Node target) {
		target.appendChild(source);
	}
	
	public static void appendTo(Node node, String sourceExpress,
			String targetExpress) {
		NodeList sources = selectNodes(node, sourceExpress);
		Node target = selectSingleNode(node, targetExpress);
		if (sources != null && target != null) {
			for (int i = 0; i < sources.getLength(); i++) {
				appendTo(sources.item(i), target);
			}
		}
	}

	/**
	 * 将源节点插入到目标节点前面
	 * 
	 * @param source
	 * @param target
	 */
	public static void insertBefore(Node source, Node target) {
		target.getParentNode().insertBefore(source, target);
	}

	public static void insertBefore(Node node, String sourceExpress,
			String targetExpress) {
		NodeList sources = selectNodes(node, sourceExpress);
		Node target = selectSingleNode(node, targetExpress);
		if (sources != null && target != null) {
			for (int i = 0; i < sources.getLength(); i++) {
				insertBefore(sources.item(i), target);
			}
		}
	}

	public static Node deleteSingleNode(Node node, String express) {
		Node del = selectSingleNode(node, express);
		if (del != null)
			return del.getParentNode().removeChild(del);

		return null;
	}

	public static void deleteNodes(Node node, String express) {
		NodeList dels = selectNodes(node, express);
		Node del;
		if (dels != null) {
			for (int i = 0; i < dels.getLength(); i++) {
				del = dels.item(i);
				if (del != null)
					del.getParentNode().removeChild(del);
			}
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
