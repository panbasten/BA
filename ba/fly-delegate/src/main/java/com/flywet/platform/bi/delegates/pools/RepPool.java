package com.flywet.platform.bi.delegates.pools;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.RepositoriesMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryMeta;
import org.pentaho.di.repository.filerep.KettleFileRepositoryMeta;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;

import com.flywet.platform.bi.core.ContextHolder;
import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.delegates.exceptions.BIKettleException;
import com.flywet.platform.bi.delegates.model.BIRepositoryFactory;

public class RepPool implements BIPoolInterface {

	public static final String REPOSITORY_TYPE_DB = "db";
	public static final String REPOSITORY_TYPE_FILE = "file";

	private static RepPool delegate;

	private AtomicBoolean init = new AtomicBoolean(false);

	private RepositoriesMeta repsMeta;

	private Map<String, ObjectPool> repsPoolMap = Collections
			.synchronizedMap(new HashMap<String, ObjectPool>());

	private RepPool() {
	}

	@Override
	public void initPool() throws BIException {
		if (!init.getAndSet(true)) {
			// 初始化资源库元数据
			initRepMeta();
			// 采用资源库连接池的方式，通过长连接保持状态，不允许相同的用户多次登录
			initRepPool();

			init.set(true);
		}
	}

	private void initRepMeta() throws BIKettleException {
		repsMeta = new RepositoriesMeta();
		try {
			repsMeta.readData();
		} catch (KettleException e) {
			throw new BIKettleException(e.getMessage(), e);
		}
	}

	private void initRepPool() {
		for (int i = 0; i < repsMeta.nrRepositories(); i++) {
			RepositoryMeta repMeta = repsMeta.getRepository(i);

			ObjectPool repPool = createRepositoryPool(repMeta);
			repsPoolMap.put(repMeta.getName(), repPool);
		}
	}

	public synchronized static RepPool instance() {
		if (delegate == null) {
			delegate = new RepPool();
		}
		return delegate;
	}

	public String[] getRepNames() {
		if (repsMeta != null && repsMeta.nrRepositories() > 0) {
			String[] rtn = new String[repsMeta.nrRepositories()];
			for (int i = 0; i < repsMeta.nrRepositories(); i++) {
				rtn[i] = repsMeta.getRepository(i).getName();
			}
			return rtn;
		}
		return null;
	}

	public String getRepType(String repName) {
		RepositoryMeta repMeta;

		if (repsMeta != null && repsMeta.nrRepositories() > 0) {
			for (int i = 0; i < repsMeta.nrRepositories(); i++) {
				if (repName.equals(repsMeta.getRepository(i).getName())) {
					repMeta = repsMeta.getRepository(i);
					if (repMeta instanceof KettleDatabaseRepositoryMeta) {
						return REPOSITORY_TYPE_DB;
					} else if (repMeta instanceof KettleFileRepositoryMeta) {
						return REPOSITORY_TYPE_FILE;
					}
				}
			}
		}

		return null;
	}

	private ObjectPool createRepositoryPool(RepositoryMeta repMeta) {
		int maxIdle = PropertyUtils
				.getIntProperty(PropertyUtils.REPOSITORY_POOL_MAXIDLE);
		int maxActive = PropertyUtils
				.getIntProperty(PropertyUtils.REPOSITORY_POOL_MAXACTIVE);

		BIRepositoryFactory repFactory = new BIRepositoryFactory(repMeta);
		GenericObjectPool.Config cfg = new GenericObjectPool.Config();
		cfg.maxActive = maxActive;
		cfg.maxIdle = maxIdle;
		cfg.testOnBorrow = true;
		cfg.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

		ObjectPool repPool = new GenericObjectPool(repFactory, cfg); // StackObjectPool(repFactory,maxIdle,initCapacity);
		return repPool;
	}

	public Repository borrowRep() throws BIKettleException {
		return borrowRep(ContextHolder.getRepositoryName(), null);
	}

	public Repository borrowRep(String repName) throws BIKettleException {
		return borrowRep(repName, null);
	}

	public Repository borrowRep(String repName, IUser userInfo)
			throws BIKettleException {
		try {
			if (!init.get()) {
				throw new BIKettleException("资源库的对象池未进行初始化.");
			}
			ObjectPool repPool = repsPoolMap.get(repName);
			if (repPool == null) {
				throw new BIKettleException("无法获得资源库的对象池.");
			}

			Repository rep = (Repository) repPool.borrowObject();
			rep.setUserInfo(userInfo);

			return rep;
		} catch (BIKettleException e) {
			throw e;
		} catch (Exception e) {
			throw new BIKettleException("从对象池中获得资源库对象出现错误.", e);
		}
	}

	public RepositoriesMeta getRepsMeta() {
		return repsMeta;
	}

	public void returnRep(Repository rep) throws BIKettleException {
		returnRep(ContextHolder.getRepositoryName(), rep);
	}

	public void returnRep(String repName, Repository rep)
			throws BIKettleException {
		if (rep == null) {
			return;
		}

		try {
			if (!init.get()) {
				throw new BIKettleException("放回对象池时出现错误");
			}

			ObjectPool repPool = repsPoolMap.get(repName);
			if (repPool != null) {
				repPool.returnObject(rep);
			}
		} catch (BIKettleException e) {
			throw e;
		} catch (Exception e) {
			throw new BIKettleException("放回对象池时出现错误", e);
		}
	}

	/**
	 * 单独创建一个资源库连接，用于外部程序调用，需要传入用户名和密码
	 * 
	 * @param repName
	 * @param username
	 * @param password
	 * @return
	 * @throws BIKettleException
	 */
	public Repository createRep(String repName, String username, String password)
			throws BIKettleException {
		try {
			RepositoryMeta repositoryMeta = repsMeta.findRepository(repName);
			Repository rep = PluginRegistry.getInstance().loadClass(
					RepositoryPluginType.class, repositoryMeta,
					Repository.class);
			rep.init(repositoryMeta);
			rep.connect(username, password);
			return rep;
		} catch (KettleException e) {
			throw new BIKettleException(e.getMessage(), e);
		}
	}

}
