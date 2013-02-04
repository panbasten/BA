package com.yonyou.bq8.di.delegates.intf;

import java.util.List;

import com.yonyou.bq8.di.delegates.model.DIAdaptorInterface;
import com.yonyou.bq8.di.delegates.vo.DIHost;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIHostAdaptor extends DIAdaptorInterface{
	public List<Object[]> getAllHosts() throws DIKettleException;
	public Object[] getHostById(long id) throws DIKettleException;
	public List<Object[]> getHostsByType(String category) throws DIKettleException;
	public void delete(long id) throws DIKettleException;
	public void addHost(DIHost host) throws DIKettleException;
	public void updateHost(DIHost host) throws DIKettleException;
}
