package com.yonyou.bq8.di.delegates.model;

import org.apache.log4j.Logger;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

import com.yonyou.bq8.di.core.ContextHolder;
import com.yonyou.bq8.di.delegates.DIEnvironmentDelegate;
import com.yonyou.bq8.di.exceptions.DIKettleException;

/**
 * 资源库操作基类
 * file资源库和DB资源库均派生自此类
 * @author han
 *
 */
public abstract class AbstractDelegate implements DIAdaptorInterface {
	private final Logger logger = Logger.getLogger(AbstractDelegate.class);
	
	protected Repository repository;
	protected String repositoryName;
	
	/**
	 * 返回repository对象至资源池
	 */
	@Override
	public void returnRepositoryQuietly() {
		try {
			DIEnvironmentDelegate.instance().returnRep(repositoryName, repository);
		} catch (DIKettleException e) {
			logger.error("return repository exception:",e);
		}
	}
	
	/**
	 * 配置repository
	 */
	@Override
	public void configRepository(String repositoryName) throws DIKettleException {
		if (repositoryName == null) {
			return;
		}
		this.repositoryName = repositoryName;
		
		doConfig();
	}

	private void doConfig() throws DIKettleException {
		Repository rep = DIEnvironmentDelegate.instance().borrowRep(repositoryName);
		if (rep == null) {
			throw new DIKettleException("资源库" + repository + "不存在.");
		}
		if (!(rep instanceof KettleDatabaseRepository)) {
			throw new DIKettleException("资源库类型不匹配");
		}
		this.repository = (KettleDatabaseRepository)rep;
	}

	@Override
	public void configRepository() throws DIKettleException {
		this.repositoryName = ContextHolder.getRepositoryName();
		doConfig();
	}
}
