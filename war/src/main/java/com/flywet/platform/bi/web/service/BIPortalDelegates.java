package com.flywet.platform.bi.web.service;

import java.util.List;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.vo.PortalMenu;

public interface BIPortalDelegates {

	/**
	 * 通过父ID获得Portal菜单
	 * 
	 * @param parentId
	 * @return
	 * @throws BIException
	 */
	public List<PortalMenu> getPortalMenusByParent(long parentId)
			throws BIException;

	/**
	 * 通过ID获得Portal菜单
	 * 
	 * @param id
	 * @return
	 * @throws BIKettleException
	 */
	public PortalMenu getPortalMenuById(long id) throws BIKettleException;

}
