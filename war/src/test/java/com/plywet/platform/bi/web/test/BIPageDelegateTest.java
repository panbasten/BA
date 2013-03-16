package com.plywet.platform.bi.web.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.plywet.platform.bi.core.exception.BIException;
import com.plywet.platform.bi.delegates.vo.FunctionType;
import com.plywet.platform.bi.web.service.BIPageDelegates;

public class BIPageDelegateTest extends WebBaseTestcase {
	@Test
	public void testGetFunctionsByParent() throws BIException {
		BIPageDelegates pageService = (BIPageDelegates) ctx
				.getBean("bi.service.pageServices");
		List<FunctionType> funcs = pageService
				.getFunctionsByParent("dbrep", 5L);
		Assert.assertNotNull(funcs);
		Assert.assertEquals(false, funcs.isEmpty());

		for (FunctionType ft : funcs) {
			System.out.println(ft.getId() + "," + ft.getCode());
		}
	}
}
