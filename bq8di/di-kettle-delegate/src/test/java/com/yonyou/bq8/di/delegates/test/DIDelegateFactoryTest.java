package com.yonyou.bq8.di.delegates.test;

import junit.framework.Assert;

import org.junit.Test;

import com.yonyou.bq8.di.delegates.intf.DIFunctionTypeAdaptor;
import com.yonyou.bq8.di.delegates.utils.DIAdaptorFactory;
import com.yonyou.bq8.di.exceptions.DIKettleException;


public class DIDelegateFactoryTest {
	@Test
	public void testInit() throws DIKettleException {
		DIFunctionTypeAdaptor di = DIAdaptorFactory.createAdaptor(DIFunctionTypeAdaptor.class,"db");
		Assert.assertNotNull(di);
	}
}
