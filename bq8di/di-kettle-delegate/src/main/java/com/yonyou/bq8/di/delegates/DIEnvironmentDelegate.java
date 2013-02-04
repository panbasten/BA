package com.yonyou.bq8.di.delegates;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Props;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.repository.IUser;
import org.pentaho.di.repository.RepositoriesMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryMeta;

import com.yonyou.bq8.di.core.utils.PropertyUtils;
import com.yonyou.bq8.di.delegates.model.RepositoryFactory;
import com.yonyou.bq8.di.exceptions.DIKettleException;

public class DIEnvironmentDelegate {
	private static DIEnvironmentDelegate delegate;
	private AtomicBoolean init = new AtomicBoolean(false);

	private AtomicBoolean initPool = new AtomicBoolean(false);

	private Props props;

	private RepositoriesMeta repsMeta;

	private Map<String, ObjectPool> repsPoolMap = Collections
			.synchronizedMap(new HashMap<String, ObjectPool>());

	private DIEnvironmentDelegate() {
	}

	public synchronized static DIEnvironmentDelegate instance()
			throws DIKettleException {
		if (delegate == null) {
			delegate = new DIEnvironmentDelegate();
		}
		return delegate;
	}

	public void init() throws DIKettleException {
		try {
			if (init.get()) {
				return;
			}

			KettleEnvironment.init();
			if (!Props.isInitialized())
				Props.init(Props.TYPE_PROPERTIES_BQDI);
			props = Props.getInstance();
			initRepMeta();
			initPageTemplate();
			init.set(true);
		} catch (DIKettleException e) {
			throw e;
		} catch (KettleException e) {
			throw new DIKettleException("初始化Kettle出现错误.", e);
		}
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

	private void initRepMeta() throws DIKettleException {
		repsMeta = new RepositoriesMeta();
		try {
			repsMeta.readData();
		} catch (KettleException e) {
			throw new DIKettleException(e.getMessage(), e);
		}
	}

	private void initPageTemplate() throws DIKettleException {
		File dir = new File(Const.getPageTemplateDirectory());
		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
		} catch (Exception e) {
			throw new DIKettleException("初始化页面模板出现错误.", e);
		}
	}

	/**
	 * 采用资源库连接池的方式，通过长连接保持状态，不允许相同的用户多次登录
	 */
	public void initRepPool() {
		if (initPool.get()) {
			return;
		}

		for (int i = 0; i < repsMeta.nrRepositories(); i++) {
			RepositoryMeta repMeta = repsMeta.getRepository(i);

			ObjectPool repPool = createRepositoryPool(repMeta);
			repsPoolMap.put(repMeta.getName(), repPool);
		}
		initPool.set(true);
	}

	private ObjectPool createRepositoryPool(RepositoryMeta repMeta) {
		int maxIdle = PropertyUtils
				.getIntProperty(PropertyUtils.REPOSITORY_POOL_MAXIDLE);
		int maxActive = PropertyUtils
				.getIntProperty(PropertyUtils.REPOSITORY_POOL_MAXACTIVE);

		RepositoryFactory repFactory = new RepositoryFactory(repMeta);
		GenericObjectPool.Config cfg = new GenericObjectPool.Config();
		cfg.maxActive = maxActive;
		cfg.maxIdle = maxIdle;
		cfg.testOnBorrow = true;
		cfg.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

		ObjectPool repPool = new GenericObjectPool(repFactory, cfg); // StackObjectPool(repFactory,maxIdle,initCapacity);
		return repPool;
	}

	public Props getProps() {
		return props;
	}

	public RepositoriesMeta getRepsMeta() {
		return repsMeta;
	}

	public Repository borrowRep(String repName) throws DIKettleException {
		return borrowRep(repName, null);
	}
	
	public Repository borrowRep(String repName, IUser userInfo)
			throws DIKettleException {
		try {
			if (!initPool.get()) {
				throw new DIKettleException("资源库的对象池未进行初始化.");
			}
			ObjectPool repPool = repsPoolMap.get(repName);
			if (repPool == null) {
				throw new DIKettleException("无法获得资源库的对象池.");
			}

			Repository rep = (Repository) repPool.borrowObject();
			rep.setUserInfo(userInfo);

			return rep;
		} catch (DIKettleException e) {
			throw e;
		} catch (Exception e) {
			throw new DIKettleException("从对象池中获得资源库对象出现错误.", e);
		}
	}

	public void returnRep(String repName, Repository rep)
			throws DIKettleException {
		if (rep == null) {
			return;
		}

		try {
			if (!initPool.get()) {
				throw new DIKettleException("放回对象池时出现错误");
			}

			ObjectPool repPool = repsPoolMap.get(repName);
			if (repPool != null) {
				repPool.returnObject(rep);
			}
		} catch (DIKettleException e) {
			throw e;
		} catch (Exception e) {
			throw new DIKettleException("放回对象池时出现错误", e);
		}
	}

	/**
	 * 单独创建一个资源库连接，用于外部程序调用，需要传入用户名和密码
	 * 
	 * @param repName
	 * @param username
	 * @param password
	 * @return
	 * @throws DIKettleException
	 */
	public Repository createRep(String repName, String username, String password)
			throws DIKettleException {
		try {
			RepositoryMeta repositoryMeta = repsMeta.findRepository(repName);
			Repository rep = PluginRegistry.getInstance().loadClass(
					RepositoryPluginType.class, repositoryMeta,
					Repository.class);
			rep.init(repositoryMeta);
			rep.connect(username, password);
			return rep;
		} catch (KettleException e) {
			throw new DIKettleException(e.getMessage(), e);
		}
	}

}
