package com.plywet.platform.bi.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.plywet.platform.bi.component.utils.FLYPageTemplateUtils;
import com.plywet.platform.bi.delegates.BIEnvironmentDelegate;
import com.plywet.platform.bi.exceptions.BIKettleException;

public class BIEnvironmentListener implements ServletContextListener {
	private final static Logger log = Logger
			.getLogger(BIEnvironmentListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			FLYPageTemplateUtils.configTemplateHome(sce.getServletContext()
					.getRealPath("pages/"));
			BIEnvironmentDelegate ed = BIEnvironmentDelegate.instance();
			ed.init();
			ed.initRepPool();
		} catch (BIKettleException e) {
			log.error("start kettle exception:", e);
		}
	}

}
