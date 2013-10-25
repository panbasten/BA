package com.flywet.platform.bi.delegates.test;

import junit.framework.Assert;

import org.junit.Test;

import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIFunctionTypeAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;

public class DIDelegateFactoryTest {
	@Test
	public void testInit() throws BIKettleException {
		BIFunctionTypeAdaptor di = BIAdaptorFactory.createAdaptor(
				BIFunctionTypeAdaptor.class, "db");
		Assert.assertNotNull(di);
	}
}
