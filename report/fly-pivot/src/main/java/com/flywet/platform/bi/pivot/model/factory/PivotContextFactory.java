package com.flywet.platform.bi.pivot.model.factory;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.context.IContext;
import com.flywet.platform.bi.pivot.model.context.PictureContext;

public class PivotContextFactory {
	public static IContext resolver(Node node) throws BIException {
		String type = Const.NVL(XMLHandler.getTagAttribute(node, "type"), null);

		if (PictureContext.CONTEXT_TAG_NAME.equalsIgnoreCase(type)) {
			return PictureContext.instance(node);
		}

		return null;
	}
}
