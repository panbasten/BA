package com.flywet.platform.bi.delegates.intf;

import java.util.List;

import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.delegates.vo.Authorization;

public interface BIAuthorizationAdaptor {
	/**
	 * 保存角色权限
	 * 
	 * @param rid
	 * @param auths
	 * @throws BIKettleException
	 */
	public void saveRoleAuth(long rid, List<Authorization> auths)
			throws BIKettleException;

	/**
	 * 删除角色权限
	 * 
	 * @param rid
	 * @throws BIKettleException
	 */
	public void delRoleAuth(long rid) throws BIKettleException;

	/**
	 * 获得角色权限
	 * 
	 * @param rid
	 * @return
	 * @throws BIKettleException
	 */
	public List<Authorization> getAuthorization(long rid)
			throws BIKettleException;
}
