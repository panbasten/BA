package com.flywet.platform.bi.web.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.delegates.BIEnvironmentDelegate;

public class WebBaseTestcase {
	protected ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		BIEnvironmentDelegate ed = BIEnvironmentDelegate.instance();
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
