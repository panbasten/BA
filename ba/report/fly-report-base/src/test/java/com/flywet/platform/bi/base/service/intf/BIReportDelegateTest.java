package com.flywet.platform.bi.base.service.intf;

import junit.framework.Assert;

import org.junit.Test;

import com.flywet.platform.bi.core.exception.BIException;

public class BIReportDelegateTest extends WebBaseTestcase {

	@Test
	public void testGetReportObject() throws BIException {
		BIReportDelegates reportService = (BIReportDelegates) ctx
				.getBean("bi.service.reportService");
		Object[] report = reportService.getReportObject(0L);
		Assert.assertNotNull(report);

		System.out
				.println("---------------testGetReportObject----------------");
		System.out.println(String.valueOf(report[1]));

		Assert.assertEquals(report[0], 0L);
	}

}
