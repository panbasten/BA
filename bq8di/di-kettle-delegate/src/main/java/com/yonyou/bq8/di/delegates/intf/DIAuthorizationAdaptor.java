package com.yonyou.bq8.di.delegates.intf;

import java.util.List;

import com.yonyou.bq8.di.delegates.vo.Authorization;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public interface DIAuthorizationAdaptor {
	public void saveRoleAuth(long rid, List<Authorization> auths) throws DIKettleException;
	public void delRoleAuth(long rid) throws DIKettleException;
	public List<Authorization> getAuthorization(long rid) throws DIKettleException;
}
