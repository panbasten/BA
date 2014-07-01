package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.vo.MetroItem;

public interface BIMetroAdaptor {

	/**
	 * 获得Metro的集合
	 * 
	 * @return
	 * @throws BIKettleException
	 */
	public List<MetroItem> getMetroItems() throws BIKettleException;

	/**
	 * 通过Id获得一个Metro对象
	 * 
	 * @param id
	 * @return
	 * @throws BIKettleException
	 */
	public MetroItem getMetroItemById(long id) throws BIKettleException;

	/**
	 * 更新Metro的对象
	 * 
	 * @param id
	 * @param obj
	 * @throws BIKettleException
	 */
	public void updateMetroObject(long id, String obj) throws BIKettleException;

}
