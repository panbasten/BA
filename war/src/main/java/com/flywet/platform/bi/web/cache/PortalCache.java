package com.flywet.platform.bi.web.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.flywet.platform.bi.delegates.vo.PortalMenu;

public class PortalCache {

	private static Map<Long, PortalMenu> PORTAL_MENU_CACHE = new ConcurrentHashMap<Long, PortalMenu>();

	public static void putPortalMenuCache(PortalMenu pm) {
		if (pm != null) {
			PORTAL_MENU_CACHE.put(pm.getId(), pm);
			List<PortalMenu> subs = pm.getChildren();
			if (subs != null) {
				for (PortalMenu subpm : subs) {
					putPortalMenuCache(subpm);
				}
			}
		}
	}

	public static PortalMenu matchPortalMenuCache(Long id) {
		return PORTAL_MENU_CACHE.get(id);
	}

	public static void PortalMenuCache() {
		PORTAL_MENU_CACHE.clear();
	}
}
