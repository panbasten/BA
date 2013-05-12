package org.pentaho.di.core.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

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
		Node n = XMLUtils.selectSingleNode(test, "//game[@id='001']");
		Node n1 = XMLUtils.selectSingleNode(test, "//game[@system='PS2']");

		assertEquals(n, n1);
	}

	public void testDeleteSingleNode() throws Exception {
		XMLUtils.deleteSingleNode(test, "//game[@system='PS2']");
		Node n = XMLUtils.selectSingleNode(test, "//game[@id='001']");
		assertNull(n);

		setUp();
	}

	public void testDeleteNodes() throws Exception {
		XMLUtils.deleteNodes(test, "//game[@system='PS2']");
		Node n1 = XMLUtils.selectSingleNode(test, "//game[@id='001']");
		Node n2 = XMLUtils.selectSingleNode(test, "//game[@id='002']");
		Node n4 = XMLUtils.selectSingleNode(test, "//game[@id='004']");
		assertNull(n1);
		assertNull(n2);
		assertNotNull(n4);

		setUp();
	}

	public void testAppendTo() throws Exception {
		Node n1 = XMLUtils.selectSingleNode(test, "//game[@id='001']");
		Node n2 = XMLUtils.selectSingleNode(test, "//game[@id='004']");
		XMLUtils.appendTo(n1, n2);
		print(test);

		setUp();
	}

	public void testInsertBefore() throws Exception {
		Node n1 = XMLUtils.selectSingleNode(test, "//game[@id='001']");
		Node n2 = XMLUtils.selectSingleNode(test, "//game[@id='004']");
		XMLUtils.insertBefore(n1, n2);
		print(test);

		setUp();
	}

	private void print(Node n) {
		try {
			System.out.println(XMLUtils.toXMLString(n));
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
