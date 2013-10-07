package com.flywet.platform.bi.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIAuthorizationAdaptor;
import com.flywet.platform.bi.delegates.intf.BIRoleAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.Authorization;
import com.flywet.platform.bi.delegates.vo.Role;
import com.flywet.platform.bi.web.cache.IdentificationCache;
import com.flywet.platform.bi.web.service.BIRoleDelegate;

@Service("bi.service.roleService")
public class BIRoleService implements BIRoleDelegate {

	private static final long ROLE_ID_ANOMYOUS = 0L;
	private static final long ROLE_ID_SUPER_ADMIN = 1L;
	private static final long ROLE_ID_ADMIN = 2L;

	@Override
	public void delRole(long roleId) throws BIKettleException {
		BIRoleAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIRoleAdaptor.class);
		adaptor.deleteRole(roleId);

		IdentificationCache.clearRoleCache(roleId);
	}

	@Override
	public List<Role> getAllRoles() throws BIKettleException {
		BIRoleAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIRoleAdaptor.class);
		return adaptor.getAllRoles();
	}

	@Override
	public Role getRoleById(long roleId) throws BIKettleException {
		Role role = IdentificationCache.matchRoleCache(roleId);
		if (role == null) {
			BIRoleAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIRoleAdaptor.class);
			role = adaptor.getRoleById(roleId);
			IdentificationCache.putRoleCache(role);
		}
		return role;
	}

	@Override
	public void saveRole(Role role) throws BIKettleException {
		BIRoleAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIRoleAdaptor.class);
		adaptor.saveRole(role);

		IdentificationCache.putRoleCache(role);
	}

	@Override
	public List<Authorization> getAuthorization(long rid)
			throws BIKettleException {
		Role role = getRoleById(rid);
		List<Authorization> auths = role.getAuths();
		if (auths == null) {
			BIAuthorizationAdaptor adaptor = BIAdaptorFactory
					.createAdaptor(BIAuthorizationAdaptor.class);
			auths = adaptor.getAuthorization(rid);
			role.setAuths(auths);
		}

		return auths;
	}

	@Override
	public void saveRoleAuthorization(long rid, List<Authorization> auths)
			throws BIKettleException {
		BIAuthorizationAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIAuthorizationAdaptor.class);
		adaptor.saveRoleAuth(rid, auths);

		Role role = getRoleById(rid);
		role.setAuths(auths);
	}

	@Override
	public boolean isSuperAdmin(long rid) {
		return (ROLE_ID_SUPER_ADMIN == rid);
	}

	@Override
	public boolean isAdmin(long rid) {
		return (ROLE_ID_ADMIN == rid);
	}

	@Override
	public boolean isAnomyous(long rid) {
		return (ROLE_ID_ANOMYOUS == rid);
	}

}
