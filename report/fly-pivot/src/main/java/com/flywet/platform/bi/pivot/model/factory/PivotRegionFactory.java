package com.flywet.platform.bi.pivot.model.factory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.IRegionObject;
import com.flywet.platform.bi.pivot.model.region.ChartRegion;
import com.flywet.platform.bi.pivot.model.region.PictureRegion;
import com.flywet.platform.bi.pivot.model.region.TableRegion;

/**
 * 透视表的区域工厂
 * 
 * @author PeterPan
 * 
 */
public class PivotRegionFactory {

	public static IRegionObject resolver(Node node) throws BIException {
		NodeList children = node.getChildNodes();

		Node childnode;

		for (int i = 0; i < children.getLength(); i++) {
			childnode = children.item(i);
			if (childnode.getNodeName().equalsIgnoreCase(
					TableRegion.REGION_OBJECT_NAME)) {
				return TableRegion.instance(childnode);
			}

			else if (childnode.getNodeName().equalsIgnoreCase(
					ChartRegion.REGION_OBJECT_NAME)) {
				return ChartRegion.instance(childnode);
			}

			else if (childnode.getNodeName().equalsIgnoreCase(
					PictureRegion.REGION_OBJECT_NAME)) {
				return PictureRegion.instance(childnode);
			}
		}

		return null;
	}

	public static String persistent(IRegionObject ro) {
		if (ro instanceof TableRegion) {
			return TableRegion.persistent((TableRegion) ro);
		}

		// TODO

		return null;
	}

}
