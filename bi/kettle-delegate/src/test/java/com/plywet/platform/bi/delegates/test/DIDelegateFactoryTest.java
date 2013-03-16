package com.plywet.platform.bi.delegates.test;

import junit.framework.Assert;

import org.junit.Test;

import com.plywet.platform.bi.delegates.intf.BIFunctionTypeAdaptor;
import com.plywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.plywet.platform.bi.exceptions.BIKettleException;

public class DIDelegateFactoryTest {
	@Test
	public void testInit() throws BIKettleException {
		BIFunctionTypeAdaptor di = BIAdaptorFactory.createAdaptor(
				BIFunctionTypeAdaptor.class, "db");
		Assert.assertNotNull(di);
	}
}
