package com.flywet.platform.bi.pivot.model.factory;

import org.w3c.dom.Node;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.PivotReport;

public class PivotReportFactory {

	/**
	 * 解析XML为PivotReport
	 * 
	 * @param doc
	 * @return
	 * @throws BIException
	 */
	public static PivotReport resolver(Node doc) throws BIException {
		PivotReport pr = PivotReport.instance(doc);
		return pr;
	}
}
