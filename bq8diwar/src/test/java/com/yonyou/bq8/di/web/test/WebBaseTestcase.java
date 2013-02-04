package com.yonyou.bq8.di.web.test;


import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yonyou.bq8.di.core.ContextHolder;
import com.yonyou.bq8.di.delegates.DIEnvironmentDelegate;

public class WebBaseTestcase {
	protected ApplicationContext ctx;
	
	@Before
	public void setUp() throws Exception {
		DIEnvironmentDelegate ed = DIEnvironmentDelegate.instance();
		ed.init();
		ed.initRepPool();
		
		ContextHolder.setRepositoryName("dbrep");
		ContextHolder.setRepositoryType("db");
		
		ctx = new ClassPathXmlApplicationContext("applicationContext-ui.xml");
	}

	@After
	public void tearDown() throws Exception {
	}

}
