package com.flywet.platform.bi.pivot.model.factory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.IRegionData;
import com.flywet.platform.bi.pivot.model.data.NormalData;
import com.flywet.platform.bi.pivot.model.data.PivotData;

/**
 * 透视表的区域数据工厂
 * 
 * @author PeterPan
 * 
 */
public class PivotRegionDataFactory {
	public static IRegionData resolver(Node node) throws BIException {
		NodeList children = node.getChildNodes();

		Node childnode;

		for (int i = 0; i < children.getLength(); i++) {
			childnode = children.item(i);
			// Pivot Data
			if (childnode.getNodeName().equalsIgnoreCase(
					PivotData.REGION_DATA_NAME)) {
				return PivotData.instance(childnode);
			}
			// Normal Data
			else if (childnode.getNodeName().equalsIgnoreCase(
					NormalData.REGION_DATA_NAME)) {
				return NormalData.instance(childnode);
			}
		}

		return null;
	}
}
