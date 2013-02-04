package com.yonyou.bq8.di.delegates.intf;

import java.util.List;

import com.yonyou.bq8.di.delegates.model.DIAdaptorInterface;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIFilesysTypeAdaptor extends DIAdaptorInterface{
	public List<Object[]> getAllFilesysType() throws DIKettleException;
}
