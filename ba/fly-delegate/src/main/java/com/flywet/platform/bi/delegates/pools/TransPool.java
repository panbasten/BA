package com.flywet.platform.bi.delegates.pools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransListener;
import org.pentaho.di.trans.TransStoppedListener;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.PropertyUtils;
import com.flywet.platform.bi.delegates.exceptions.BIPoolException;
import com.flywet.platform.bi.delegates.vo.TransPoolWapper;

public class TransPool implements BIPoolInterface {

	private static TransPool delegate;

	private AtomicBoolean init = new AtomicBoolean(false);

	private int maxPoolSize = 3;

	private int maxActiveSize = 3;

	private List<TransPoolWapper> requests = Collections
			.synchronizedList(new ArrayList<TransPoolWapper>());

	private Map<Trans, TransPoolWapper> actives = Collections
			.synchronizedMap(new HashMap<Trans, TransPoolWapper>());

	@Override
	public void initPool() throws BIException {
		if (!init.getAndSet(true)) {
			// 初始化poolSize
			maxPoolSize = PropertyUtils
					.getIntProperty(PropertyUtils.TRANS_POOL_MAX_SIZE);

			// 初始化运行队列
			maxActiveSize = PropertyUtils
					.getIntProperty(PropertyUtils.TRANS_POOL_MAX_ACTIVE_SIZE);

			init.set(true);
		}
	}

	public synchronized static TransPool instance() throws BIException {
		if (delegate == null) {
			delegate = new TransPool();
			delegate.initPool();
		}
		return delegate;
	}

	public synchronized void offer(TransPoolWapper vo) throws BIException {
		if (actives.size() >= maxActiveSize) {
			if (requests.size() >= maxPoolSize) {
				throw new BIPoolException("超出最大缓存值");
			} else {
				requests.add(vo);
			}
		} else {
			run(vo);
		}
	}

	public synchronized void poll(Trans trans) {
		actives.remove(trans);
		if (this.requests.isEmpty()) {
			return;
		}

		TransPoolWapper vo = this.requests.remove(0);
		run(vo);
	}

	public void run(TransPoolWapper vo) {
		try {
			Trans trans = new Trans(vo.getTransMeta());
			// trans.setRepository(rep);
			trans.addTransListener(new TransPoolListener());
			trans.addTransStoppedListener(new TransPoolStoppedListener());

			trans.initializeVariablesFrom(null);
			trans.getTransMeta().setInternalKettleVariables(trans);
			// trans.setLogLevel(log.getLogLevel());

			String[] transParams = trans.listParameters();
			for (String param : transParams) {
				String value = vo.getParamValue(param);
				if (value != null) {
					trans.setParameterValue(param, value);
				}
			}

			trans.activateParameters();

			trans.execute(null);
			// trans.waitUntilFinished();

			// 参考Pan.java 390

			// TODO

			actives.put(trans, vo);

		} catch (Exception e) {

		}

	}
}

class TransPoolStoppedListener implements TransStoppedListener {

	@Override
	public void transStopped(Trans trans) throws KettleException {
		try {
			TransPool.instance().poll(trans);
		} catch (Exception e) {
			throw new KettleException("执行转换运行池执行出现错误。");
		}
	}

}

class TransPoolListener implements TransListener {

	@Override
	public void transActive(Trans trans) {

	}

	@Override
	public void transFinished(Trans trans) throws KettleException {
		try {
			TransPool.instance().poll(trans);
		} catch (Exception e) {
			throw new KettleException("执行转换运行池执行出现错误。");
		}
	}

	@Override
	public void transIdle(Trans trans) {

	}

	@Override
	public void transStarted(Trans trans) throws KettleException {

	}

}