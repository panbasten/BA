package com.flywet.platform.dk.multi;

import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class EigenbaseXmlTest {

	public void testEigenbase() {
		try {
			Document doc = EigenbaseXmlUtil
					.loadXmlFile("C:/_D/_code/GitHub/BI/dk/multi-tools-plugin/src/test/resource/MondrianResource.xml");
			Node rb = EigenbaseXmlUtil.getSubNode(doc, "resourceBundle");
			List<Node> exList = EigenbaseXmlUtil.getSubNodes(rb, "exception");
			List<Node> mList = EigenbaseXmlUtil.getSubNodes(rb, "message");

			String n, v;
			for (Node ex : exList) {
				n = EigenbaseXmlUtil.getTagOrAttribute(ex, "name");
				v = EigenbaseXmlUtil.getTagOrAttribute(ex, "text");
				System.out.println(Utils.trim(n) + "=" + Utils.trim(v));
			}
			for (Node ex : mList) {
				n = EigenbaseXmlUtil.getTagOrAttribute(ex, "name");
				v = EigenbaseXmlUtil.getTagOrAttribute(ex, "text");
				System.out.println(Utils.trim(n) + "=" + Utils.trim(v));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
