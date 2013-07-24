package com.flywet.platform.bi.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.intf.BIAuthorizationAdaptor;
import com.flywet.platform.bi.delegates.intf.BIRoleAdaptor;
import com.flywet.platform.bi.delegates.utils.BIAdaptorFactory;
import com.flywet.platform.bi.delegates.vo.Authorization;
import com.flywet.platform.bi.delegates.vo.Role;
import com.flywet.platform.bi.web.service.BIRoleDelegate;

@Service("bi.service.roleService")
public class BIRoleService implements BIRoleDelegate {

	@Override
	public void delRole(long roleId) throws BIKettleException {
		BIRoleAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIRoleAdaptor.class);
		adaptor.deleteRole(roleId);
	}

	@Override
	public List<Role> getAllRoles() throws BIKettleException {
		BIRoleAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIRoleAdaptor.class);
		return adaptor.getAllRoles();
	}

	@Override
	public Role getRoleById(long roleId) throws BIKettleException {
		BIRoleAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIRoleAdaptor.class);
		return adaptor.getRoleById(roleId);
	}

	@Override
	public void saveRole(Role role) throws BIKettleException {
		BIRoleAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIRoleAdaptor.class);
		adaptor.saveRole(role);
	}

	@Override
	public List<Authorization> getAuthorization(long rid)
			throws BIKettleException {
		BIAuthorizationAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIAuthorizationAdaptor.class);
		return adaptor.getAuthorization(rid);
	}

	@Override
	public void saveRoleAuthorization(long rid, List<Authorization> auths)
			throws BIKettleException {
		BIAuthorizationAdaptor adaptor = BIAdaptorFactory
				.createAdaptor(BIAuthorizationAdaptor.class);
		adaptor.saveRoleAuth(rid, auths);
	}

}
