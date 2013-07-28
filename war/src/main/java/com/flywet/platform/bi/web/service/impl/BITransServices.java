package com.flywet.platform.bi.web.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.delegates.BIEnvironmentDelegate;
import com.flywet.platform.bi.delegates.enums.BIDirectoryCategory;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.web.cache.TransOrJobMetaCache;
import com.flywet.platform.bi.web.service.BITransDelegates;
import com.flywet.platform.bi.web.utils.DIUtils;

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
	 * 创建一个转换
	 * 
	 * @param user
	 * @param repository
	 * @param dirId
	 * @param desc
	 * @return
	 * @throws BIKettleException
	 */
	@Override
	public TransMeta createTransformation(IUser user, String repository,
			Long dirId, String desc) throws BIKettleException {
		Repository rep = null;
		try {
			rep = BIEnvironmentDelegate.instance().borrowRep(repository, null);

			RepositoryDirectoryInterface dir = DIUtils.getDirecotry(rep, dirId,
					BIDirectoryCategory.DI);
			if (checkTransMetaByName(rep, dir, desc)) {
				throw new BIKettleException("存在重名的转换");
			}

			TransMeta transMeta = new TransMeta();
			transMeta.clear();
			transMeta.initializeVariablesFrom(null);

			// 创建信息
			Date now = new Date();
			transMeta.setCreatedDate(now);
			transMeta.setModifiedDate(now);
			transMeta.setCreatedUser(user.getLogin());
			transMeta.setModifiedUser(user.getLogin());

			// dir
			transMeta.setRepositoryDirectory(dir);

			// name
			transMeta.setName(desc);

			this.save(rep, transMeta);

			return transMeta;
		} catch (BIKettleException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("创建转换出现异常", ex);
			throw new BIKettleException("创建转换出现异常");
		} finally {
			BIEnvironmentDelegate.instance().returnRep(repository, rep);
		}
	}

	/**
	 * 通过转换的名称判断转换是否存在
	 * 
	 * @param rep
	 * @param dirId
	 * @param name
	 * @return
	 * @throws BIKettleException
	 */
	private boolean checkTransMetaByName(Repository rep,
			RepositoryDirectoryInterface dir, String name)
			throws BIKettleException {
		try {
			ObjectId transId = rep.getTransformationID(name, dir);
			return transId != null;
		} catch (KettleException e) {
			log.error("通过名称获得转换出现错误");
			throw new BIKettleException("通过名称获得转换出现错误");
		}

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
	 * @param transMeta
	 */
	@Override
	public void updateCacheTransformation(String repository, TransMeta transMeta) {
		if (transMeta.getObjectId() == null) {
			log.warn("由于无法找到ID，该转换并未更新到缓存");
			return;
		}
		TransOrJobMetaCache.putTrans(repository, Long.valueOf(transMeta
				.getObjectId().getId()), transMeta);
	}

}
