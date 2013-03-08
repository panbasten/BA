package com.yonyou.bq8.di.delegates.intf;

import java.util.List;

import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIReportAdaptor {
	public List<Object[]> getSubDirectoryObjects(String dirId)
			throws DIKettleException;
}
