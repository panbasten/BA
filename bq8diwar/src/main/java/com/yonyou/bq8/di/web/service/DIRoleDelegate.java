package com.yonyou.bq8.di.web.service;

import java.util.List;

import com.yonyou.bq8.di.delegates.vo.Authorization;
import com.yonyou.bq8.di.delegates.vo.Role;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIRoleDelegate {
	public List<Role> getAllRoles()throws DIKettleException;
	public Role getRoleById(long roleId)throws DIKettleException;
	public void saveRole(Role role)throws DIKettleException;
	public void delRole(long roleId) throws DIKettleException;
	public List<Authorization> getAuthorization(long rid) throws DIKettleException;
	public void saveRoleAuthorization(long rid,List<Authorization> auths) throws DIKettleException;
}
