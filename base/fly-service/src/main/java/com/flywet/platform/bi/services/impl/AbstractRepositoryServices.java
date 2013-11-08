package com.flywet.platform.bi.services.impl;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryElementInterface;

import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.core.pools.RepPool;

public abstract class AbstractRepositoryServices {

	private final Logger log = Logger
			.getLogger(AbstractRepositoryServices.class);

	/**
	 * 保存资源库对象，包括：RepositoryObjectType枚举类型中的
	 * 
	 * @param repository
	 * @param repositoryElement
	 * @throws BIKettleException
	 */
	public void save(RepositoryElementInterface repositoryElement)
			throws BIKettleException {
		Repository rep = null;
		try {
			rep = RepPool.instance().borrowRep();
			save(rep, repositoryElement);
		} catch (Exception ex) {
			log.error("通过ID保存对象出现异常", ex);
		} finally {
			RepPool.instance().returnRep(rep);
		}
	}

	public void save(Repository rep,
			RepositoryElementInterface repositoryElement)
			throws KettleException {
		rep.save(repositoryElement, null, null);
	}
}
