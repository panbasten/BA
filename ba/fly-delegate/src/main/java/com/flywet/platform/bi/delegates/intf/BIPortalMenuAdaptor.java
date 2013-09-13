package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.model.BIAdaptorInterface;
import com.flywet.platform.bi.delegates.vo.PortalMenu;

public interface BIPortalMenuAdaptor extends BIAdaptorInterface {
	/**
	 * 通过父ID获得Portal的菜单
	 * 
	 * @param id
	 * @return
	 * @throws BIKettleException
	 */
	public List<PortalMenu> getPortalMenuByParent(long id)
			throws BIKettleException;
}
