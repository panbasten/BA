package com.yonyou.bq8.di.delegates.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.yonyou.bq8.di.delegates.anno.DIDelegate;
import com.yonyou.bq8.di.delegates.intf.DIReportAdaptor;
import com.yonyou.bq8.di.delegates.model.AbstractDbAdaptor;
import com.yonyou.bq8.di.exceptions.DIKettleException;

@DIDelegate(type = "db")
public class DIReportAdaptorImpl extends AbstractDbAdaptor implements
		DIReportAdaptor {

	private final Logger logger = Logger.getLogger(DIReportAdaptorImpl.class);

	@Override
	public List<Object[]> getRoots() throws DIKettleException {
		// TODO Auto-generated method stub
		return null;
	}

}
