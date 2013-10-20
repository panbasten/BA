package com.flywet.platform.bi.delegates.model;

import org.apache.log4j.Logger;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;

import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.pools.RepPool;

/**
 * 资源库操作基类 file资源库和DB资源库均派生自此类
 * 
 * @author han
 * 
 */
public abstract class BIAbstractDelegate implements BIAdaptorInterface {
	private final Logger logger = Logger.getLogger(BIAbstractDelegate.class);

	protected Repository repository;
	protected String repositoryName;

	/**
	 * 返回repository对象至资源池
	 */
	@Override
	public void returnRepositoryQuietly() {
		try {
			RepPool.instance().returnRep(repositoryName, repository);
		} catch (BIKettleException e) {
			logger.error("return repository exception:", e);
		}
	}

	/**
	 * 配置repository
	 */
	@Override
	public void configRepository(String repositoryName)
			throws BIKettleException {
		if (repositoryName == null) {
			return;
		}
		this.repositoryName = repositoryName;

		doConfig();
	}

	private void doConfig() throws BIKettleException {
		Repository rep = RepPool.instance().borrowRep(repositoryName);
		if (rep == null) {
			throw new BIKettleException("资源库" + repository + "不存在.");
		}
		if (!(rep instanceof KettleDatabaseRepository)) {
			throw new BIKettleException("资源库类型不匹配");
		}
		this.repository = (KettleDatabaseRepository) rep;
	}

	@Override
	public void configRepository() throws BIKettleException {
		this.repositoryName = ContextHolder.getRepositoryName();
		doConfig();
	}
}
