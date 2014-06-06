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

}
