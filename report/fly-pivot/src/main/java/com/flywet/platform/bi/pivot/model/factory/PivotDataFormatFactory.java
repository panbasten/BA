package com.flywet.platform.bi.pivot.model.factory;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.dataformat.DataBarDataFormat;
import com.flywet.platform.bi.pivot.model.dataformat.IDataFormat;
import com.flywet.platform.bi.pivot.model.dataformat.IconDataFormat;
import com.flywet.platform.bi.pivot.model.dataformat.SparkDataFormat;
import com.flywet.platform.bi.pivot.model.dataformat.StyleDataFormat;

public class PivotDataFormatFactory {

	public static IDataFormat resolver(Node node) throws BIException {
		String type = Const.NVL(XMLHandler.getTagAttribute(node, "type"), null);

		if (IconDataFormat.DATA_FORMAT_TYPE.equalsIgnoreCase(type)) {
			return IconDataFormat.instance(node);
		} else if (StyleDataFormat.DATA_FORMAT_TYPE.equalsIgnoreCase(type)) {
			return StyleDataFormat.instance(node);
		} else if (DataBarDataFormat.DATA_FORMAT_TYPE.equalsIgnoreCase(type)) {
			return DataBarDataFormat.instance(node);
		} else if (SparkDataFormat.DATA_FORMAT_TYPE.equalsIgnoreCase(type)) {
			return SparkDataFormat.instance(node);
		}

		return null;
	}

}
