package com.flywet.platform.bi.services.intf;

import org.pentaho.di.repository.RepositoryElementInterface;

import com.flywet.platform.bi.core.exception.BIKettleException;

/**
 * 资源库代理
 * 
 * @author PeterPan
 * 
 */
public interface BIRepositoryDelegates {

	/**
	 * 保存资源库对象，包括：RepositoryObjectType枚举类型中的
	 * 
	 * @param repository
	 * @param repositoryElement
	 * @throws BIKettleException
	 */
	public void save(RepositoryElementInterface repositoryElement)
			throws BIKettleException;
}
