package com.plywet.platform.bi.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.plywet.platform.bi.core.utils.XmlUtils;

public class XmlUtilsTest extends TestCase {

	private Document test;

	@Override
	protected void setUp() throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder db = factory.newDocumentBuilder();
		File file = new File(XmlUtilsTest.class.getResource("test.xml")
				.getFile());
		test = db.parse(file);
	}

	public void testSelectSingleNode() {
		Node n = XmlUtils.selectSingleNode(test, "//game[@id='001']");
		Node n1 = XmlUtils.selectSingleNode(test, "//game[@system='PS2']");

		assertEquals(n, n1);
	}

	public void testDeleteSingleNode() throws Exception {
		XmlUtils.deleteSingleNode(test, "//game[@system='PS2']");
		Node n = XmlUtils.selectSingleNode(test, "//game[@id='001']");
		assertNull(n);

		setUp();
	}

	public void testDeleteNodes() throws Exception {
		XmlUtils.deleteNodes(test, "//game[@system='PS2']");
		Node n1 = XmlUtils.selectSingleNode(test, "//game[@id='001']");
		Node n2 = XmlUtils.selectSingleNode(test, "//game[@id='002']");
		Node n4 = XmlUtils.selectSingleNode(test, "//game[@id='004']");
		assertNull(n1);
		assertNull(n2);
		assertNotNull(n4);

		setUp();
	}

	public void testAppendTo() throws Exception {
		Node n1 = XmlUtils.selectSingleNode(test, "//game[@id='001']");
		Node n2 = XmlUtils.selectSingleNode(test, "//game[@id='004']");
		XmlUtils.appendTo(n1, n2);
		print(test);

		setUp();
	}

	public void testInsertBefore() throws Exception {
		Node n1 = XmlUtils.selectSingleNode(test, "//game[@id='001']");
		Node n2 = XmlUtils.selectSingleNode(test, "//game[@id='004']");
		XmlUtils.insertBefore(n1, n2);
		print(test);

		setUp();
	}

	private void print(Node n) {
		try {
			System.out.println(XmlUtils.toXMLString(n));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
