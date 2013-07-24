package com.flywet.platform.bi.web.service.impl;

import org.apache.log4j.Logger;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.TransMeta;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.delegates.BIEnvironmentDelegate;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.web.cache.TransOrJobMetaCache;
import com.flywet.platform.bi.web.service.BITransDelegates;

@Service("bi.service.transServices")
public class BITransServices extends AbstractRepositoryServices implements
		BITransDelegates {

	private final Logger log = Logger.getLogger(BITransServices.class);

	/**
	 * 通过ID获得转换
	 * 
	 * @param id
	 * @throws BIKettleException
	 */
	@Override
	public TransMeta loadTransformation(String repository, Long id)
			throws BIKettleException {
		TransMeta transMeta = TransOrJobMetaCache.getTrans(repository, id);
		if (transMeta == null) {
			Repository rep = null;
			try {
				rep = BIEnvironmentDelegate.instance().borrowRep(repository,
						null);
				transMeta = rep.loadTransformation(new LongObjectId(id), null);
				TransOrJobMetaCache.putTrans(repository, id, transMeta);
			} catch (Exception ex) {
				log.error("通过ID获得转换出现异常", ex);
			} finally {
				BIEnvironmentDelegate.instance().returnRep(repository, rep);
			}
		}
		return transMeta;
	}

	/**
	 * 通过ID清除缓存
	 * 
	 * @param id
	 * @throws BIKettleException
	 */
	@Override
	public TransMeta clearCacheTransformation(String repository, Long id) {
		return TransOrJobMetaCache.clearTrans(repository, id);
	}

	/**
	 * 更新转换
	 * 
	 * @param repository
	 * @param id
	 * @param transMeta
	 */
	@Override
	public void updateCacheTransformation(String repository, Long id,
			TransMeta transMeta) {
		TransOrJobMetaCache.putTrans(repository, id, transMeta);
	}

}
