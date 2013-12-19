package com.flywet.platform.bi.smart.service.intf;

import junit.framework.Assert;
import org.junit.Test;
import com.flywet.platform.bi.core.exception.BIException;

public class BISmartDelegatesTest extends WebBaseTestcase {

	@Test
	public void testGetSmartObject() throws BIException {
		BISmartDelegates smartService = (BISmartDelegates) ctx
				.getBean("bi.service.smartService");
		Object[] smart = smartService.getSmartObject(0L);
		Assert.assertNotNull(smart);

		System.out.println("---------------testGetSmartObject----------------");
		System.out.println(String.valueOf(smart[2]));

		Assert.assertEquals(smart[0], 0L);
	}

}
