package com.flywet.platform.bi.pivot.model.factory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flywet.platform.bi.pivot.model.IRegionData;
import com.flywet.platform.bi.pivot.model.data.PivotData;

/**
 * 透视表的区域数据工厂
 * 
 * @author PeterPan
 * 
 */
public class PivotRegionDataFactory {
	public static IRegionData resolver(Node node) {
		NodeList children = node.getChildNodes();

		Node childnode;

		for (int i = 0; i < children.getLength(); i++) {
			childnode = children.item(i);
			if (childnode.getNodeName().equalsIgnoreCase("PivotData")) {
				return PivotData.instance(childnode);
			}
		}

		return null;
	}
}
