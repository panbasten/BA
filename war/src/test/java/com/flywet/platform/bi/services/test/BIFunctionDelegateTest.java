package com.flywet.platform.bi.services.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.vo.FunctionType;
import com.flywet.platform.bi.services.intf.BIFunctionDelegates;

public class BIFunctionDelegateTest extends WebBaseTestcase {
	@Test
	public void testGetFunctionsByParent() throws BIException {
		BIFunctionDelegates pageService = (BIFunctionDelegates) ctx
				.getBean("bi.service.funcServices");
		List<FunctionType> funcs = pageService.getFunctionsByParent(5L);
		Assert.assertNotNull(funcs);
		Assert.assertEquals(false, funcs.isEmpty());

		for (FunctionType ft : funcs) {
			System.out.println(ft.getId() + "," + ft.getCode());
		}
	}
}
