package com.flywet.platform.bi.pivot.service.intf;

import org.json.simple.JSONObject;
import org.junit.Test;
import org.pentaho.di.core.xml.XMLHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.flywet.platform.bi.base.service.intf.BIReportDelegates;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.PivotReport;
import com.flywet.platform.bi.pivot.model.factory.PivotReportFactory;

public class BIPivotDelegatesTest extends WebBaseTestcase {

	@Test
	public void testQueryMdx() throws BIException {
		BIPivotDelegates pivotService = (BIPivotDelegates) ctx
				.getBean("bi.service.pivotService");
		BIReportDelegates reportService = (BIReportDelegates) ctx
				.getBean("bi.service.reportService");

		long id = 11L;

		Object[] report = reportService.getReportObject(Long.valueOf(id));
		Document doc = PageTemplateInterpolator
				.getDomWithContent((String) report[1]);

		Node prNode = XMLHandler.getSubNode(doc, "PivotReport");

		PivotReport pr = PivotReportFactory.resolver(prNode);

		JSONObject jo = pivotService.query(pr);

		System.out.println(jo.toJSONString());
	}

}
