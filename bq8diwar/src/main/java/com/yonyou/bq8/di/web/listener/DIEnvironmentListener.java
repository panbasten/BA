package com.yonyou.bq8.di.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.yonyou.bq8.di.component.utils.BQPageTemplateUtils;
import com.yonyou.bq8.di.delegates.DIEnvironmentDelegate;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public class DIEnvironmentListener implements ServletContextListener {
	private final static Logger log = Logger.getLogger(DIEnvironmentListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			BQPageTemplateUtils.configTemplateHome(sce.getServletContext().getRealPath("pages/"));
			DIEnvironmentDelegate ed = DIEnvironmentDelegate.instance();
			ed.init();
			ed.initRepPool();
		} catch (DIKettleException e) {
			log.error("start kettle exception:",e);
		}
	}

}
