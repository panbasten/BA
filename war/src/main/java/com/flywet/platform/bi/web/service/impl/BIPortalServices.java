package com.flywet.platform.bi.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIPortalMenuAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.PortalAction;
import com.flywet.platform.bi.delegates.vo.PortalMenu;
import com.flywet.platform.bi.web.cache.PortalCache;
import com.flywet.platform.bi.web.service.BIPortalDelegates;

@Service("bi.service.portalServices")
public class BIPortalServices implements BIPortalDelegates {

	@Override
	public List<PortalMenu> getPortalMenusByParent(long parentId)
			throws BIException {
		PortalMenu pm = getPortalMenuById(parentId);
		return pm.getChildren();
	}

	@Override
	public PortalMenu getPortalMenuById(long id) throws BIKettleException {
		PortalMenu pm = PortalCache.matchPortalMenuCache(id);
		if (pm == null) {
			BIPortalMenuAdaptor portalMenuDelegate = BIAdaptorFactory
					.createAdaptor(BIPortalMenuAdaptor.class);
			pm = portalMenuDelegate.getPortalMenuById(id);

			List<PortalMenu> portalMenus = getSubPortalMenus(
					portalMenuDelegate, id);
			pm.setChildren(portalMenus);

			PortalCache.putPortalMenuCache(pm);
		}
		return pm;
	}

	@Override
	public PortalAction getPortalActionById(long id) throws BIKettleException {
		PortalAction pa = PortalCache.matchPortalActionCache(id);
		if (pa == null) {
			BIPortalMenuAdaptor portalMenuDelegate = BIAdaptorFactory
					.createAdaptor(BIPortalMenuAdaptor.class);
			pa = portalMenuDelegate.getPortalAction(id);

			PortalCache.putPortalActionCache(pa);
		}
		return pa;
	}

	private List<PortalMenu> getSubPortalMenus(
			BIPortalMenuAdaptor portalMenuDelegate, long parentId)
			throws BIKettleException {
		List<PortalMenu> portalMenus = portalMenuDelegate
				.getPortalMenuByParent(parentId);

		for (PortalMenu pm : portalMenus) {
			List<PortalMenu> children = getSubPortalMenus(portalMenuDelegate,
					pm.getId());
			pm.setChildren(children);
		}
		return portalMenus;
	}

}
