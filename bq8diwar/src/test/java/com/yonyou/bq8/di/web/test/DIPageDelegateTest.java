package com.yonyou.bq8.di.web.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.yonyou.bq8.di.core.exception.DIException;
import com.yonyou.bq8.di.delegates.vo.FunctionType;
import com.yonyou.bq8.di.web.service.DIPageDelegates;

public class DIPageDelegateTest extends WebBaseTestcase{
	@Test
	public void testGetFunctionsByParent() throws DIException {
        DIPageDelegates pageService = (DIPageDelegates) ctx.getBean("di.service.pageServices");
        List<FunctionType> funcs = pageService.getFunctionsByParent("dbrep", 5L);
        Assert.assertNotNull(funcs);
        Assert.assertEquals(false, funcs.isEmpty());
        
        for (FunctionType ft : funcs) {
        	System.out.println(ft.getId() + "," + ft.getCode());
        }
	}
}
