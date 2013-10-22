package com.flywet.platform.bi.delegates.pools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.utils.PropertyUtils;

public class TransPool implements BIPoolInterface {

	private static TransPool delegate;

	private AtomicBoolean init = new AtomicBoolean(false);

	private int maxPoolSize = 3;

	private int maxActiveSize = 3;

	private List<TransPoolVo> requests = Collections
			.synchronizedList(new ArrayList<TransPoolVo>());

	private Map<Long, TransPoolVo> actives = Collections
			.synchronizedMap(new HashMap<Long, TransPoolVo>());

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

	public void offer(TransMeta transMeta) {
		synchronized (actives) {
			if (actives.size() >= maxActiveSize) {
				synchronized (requests) {
					if (requests.size() >= maxPoolSize) {
//						throw new 
					}else{
						
					}
				}
			} else {
//				run(transMeta);
			}
		}
	}
}

class TransPoolVo {

	// 日志ID
	private long logId;
	// 唯一标识
	private String single;
	// 转换
	private TransMeta transMeta;
	// 提交时间
	private Date submitTime;
	// 提交用户
	private String submitUser;
	// 执行时间
	private Date startTime;
	// 执行结束时间
	private Date finishTime;

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public TransMeta getTransMeta() {
		return transMeta;
	}

	public void setTrans(TransMeta transMeta) {
		this.transMeta = transMeta;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getSubmitUser() {
		return submitUser;
	}

	public void setSubmitUser(String submitUser) {
		this.submitUser = submitUser;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

}
