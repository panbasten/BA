package com.yonyou.bq8.di.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.bq8.di.delegates.intf.DIAuthorizationAdaptor;
import com.yonyou.bq8.di.delegates.intf.DIRoleAdaptor;
import com.yonyou.bq8.di.delegates.utils.DIAdaptorFactory;
import com.yonyou.bq8.di.delegates.vo.Authorization;
import com.yonyou.bq8.di.delegates.vo.Role;
import com.yonyou.bq8.di.exceptions.DIKettleException;
import com.yonyou.bq8.di.web.service.DIRoleDelegate;

@Service("di.service.roleService")
public class DIRoleService implements DIRoleDelegate {

	@Override
	public void delRole(long roleId) throws DIKettleException {
		DIRoleAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIRoleAdaptor.class);
		adaptor.deleteRole(roleId);
	}

	@Override
	public List<Role> getAllRoles() throws DIKettleException {
		DIRoleAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIRoleAdaptor.class);
		return adaptor.getAllRoles();
	}

	@Override
	public Role getRoleById(long roleId) throws DIKettleException {
		DIRoleAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIRoleAdaptor.class);
		return adaptor.getRoleById(roleId);
	}

	@Override
	public void saveRole(Role role) throws DIKettleException {
		DIRoleAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIRoleAdaptor.class);
		adaptor.saveRole(role);
	}

	@Override
	public List<Authorization> getAuthorization(long rid) throws DIKettleException {
		DIAuthorizationAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIAuthorizationAdaptor.class);
		return adaptor.getAuthorization(rid);
	}

	@Override
	public void saveRoleAuthorization(long rid, List<Authorization> auths) throws DIKettleException {
		DIAuthorizationAdaptor adaptor = DIAdaptorFactory.createAdaptor(DIAuthorizationAdaptor.class);
		adaptor.saveRoleAuth(rid, auths);
	}

}
