package com.flywet.platform.bi.pivot.service.intf;

import org.junit.Test;

import com.flywet.platform.bi.core.exception.BIException;

public class BIPivotDelegatesTest extends WebBaseTestcase {

	@Test
	public void testQueryMdx() throws BIException {
		BIPivotDelegates pivotService = (BIPivotDelegates) ctx
				.getBean("bi.service.pivotService");
		pivotService.queryMdx();
	}

}
