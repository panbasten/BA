package com.flywet.platform.bi.delegates;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Props;
import org.pentaho.di.core.exception.KettleException;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIKettleException;
import com.flywet.platform.bi.core.pools.RepPool;

public class BIEnvironmentDelegate {
	private static BIEnvironmentDelegate delegate;

	private AtomicBoolean init = new AtomicBoolean(false);

	private Props props;

	private BIEnvironmentDelegate() {
	}

	public synchronized static BIEnvironmentDelegate instance()
			throws BIKettleException {
		if (delegate == null) {
			delegate = new BIEnvironmentDelegate();
		}
		return delegate;
	}

	public void init() throws BIException {
		try {
			if (!init.getAndSet(true)) {
				KettleEnvironment.init();
				if (!Props.isInitialized())
					Props.init(Props.TYPE_PROPERTIES_BI);
				props = Props.getInstance();
				initPageTemplate();

				// 初始化资源库对象池
				RepPool.instance().initPool();

				init.set(true);
			}
		} catch (BIException e) {
			throw e;
		} catch (KettleException e) {
			throw new BIKettleException("初始化Kettle出现错误.", e);
		}
	}

	private void initPageTemplate() throws BIKettleException {
		File dir = new File(Const.getPageTemplateDirectory());
		try {
			if (!dir.exists()) {
				dir.mkdirs();
			}
		} catch (Exception e) {
			throw new BIKettleException("初始化页面模板出现错误.", e);
		}
	}

	public Props getProps() {
		return props;
	}

}
