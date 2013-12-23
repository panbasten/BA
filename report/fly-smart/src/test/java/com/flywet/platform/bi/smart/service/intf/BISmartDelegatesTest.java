package com.flywet.platform.bi.smart.service.intf;

import junit.framework.Assert;

import org.junit.Test;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.smart.model.BISmartMeta;

public class BISmartDelegatesTest extends WebBaseTestcase {

	@Test
	public void testGetSmartObject() throws BIException {
		BISmartDelegates smartService = (BISmartDelegates) ctx
				.getBean("bi.service.smartService");
		BISmartMeta smart = smartService.getSmartObject(0L);
		Assert.assertNotNull(smart);

		System.out.println("---------------testGetSmartObject----------------");
		System.out.println(smart.getSmartObject());

		Assert.assertEquals(smart.getIdSmart().longValue(), 0L);
	}

}
