package com.flywet.platform.bi.smart.utils;

import mondrian.olap.Util.PropertyList;
import mondrian.spi.DynamicSchemaProcessor;

import com.flywet.platform.bi.delegates.utils.BIWebUtils;
import com.flywet.platform.bi.smart.model.BISmartMeta;
import com.flywet.platform.bi.smart.service.intf.BISmartDelegates;

public class BIOlapSchemaProcessor implements DynamicSchemaProcessor {

	@Override
	public String processSchema(String schemaUrl, PropertyList connectInfo)
			throws Exception {
		BISmartDelegates smartDelegates = BIWebUtils
				.getBean("bi.service.smartService");

		BISmartMeta sm = smartDelegates.getSmartObject(Long.valueOf(schemaUrl));

		return sm.getSmartObject();
	}

}
