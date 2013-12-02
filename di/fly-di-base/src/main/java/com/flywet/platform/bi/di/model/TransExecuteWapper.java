package com.flywet.platform.bi.di.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.pentaho.di.trans.TransMeta;

import com.flywet.platform.bi.core.ContextHolder;

public class TransExecuteWapper {

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
	// 命名参数
	private Map<String, String> params = new HashMap<String, String>();

	public TransExecuteWapper(TransMeta transMeta) {
		this.submitTime = new Date();
		this.submitUser = ContextHolder.getLoginName();
		this.transMeta = transMeta;
	}

	public TransExecuteWapper(String single, TransMeta transMeta) {
		this(transMeta);
		this.single = single;
	}

	public String getParamValue(String key) {
		return params.get(key);
	}

	public String putParam(String key, String value) {
		return params.put(key, value);
	}

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

	public void setTransMeta(TransMeta transMeta) {
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
