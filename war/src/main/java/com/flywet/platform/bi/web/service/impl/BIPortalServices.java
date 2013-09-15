package com.flywet.platform.bi.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.BIEnvironmentDelegate;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIPortalMenuAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.PortalMenu;
import com.flywet.platform.bi.web.service.BIPortalDelegates;

@Service("bi.service.portalServices")
public class BIPortalServices implements BIPortalDelegates {

	@Override
	public void registerRepository(String rep) throws BIKettleException {
		BIEnvironmentDelegate ed = BIEnvironmentDelegate.instance();
		ed.init();
		ed.initRepPool();

		ContextHolder.setRepositoryName(rep);
		ContextHolder.setRepositoryType(ed.getRepType(rep));
	}

	@Override
	public List<PortalMenu> getPortalMenusByParent(long parentId)
			throws BIException {
		BIPortalMenuAdaptor portalMenuDelegate = BIAdaptorFactory
				.createAdaptor(BIPortalMenuAdaptor.class);
		List<PortalMenu> portalMenus = portalMenuDelegate
				.getPortalMenuByParent(parentId);

		for (PortalMenu pm : portalMenus) {
			List<PortalMenu> children = getPortalMenusByParent(pm.getId());
			pm.setChildren(children);
		}
		return portalMenus;
	}

}
