package com.yonyou.bq8.di.web.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.core.utils.JSONUtils;

public class ActionMessage {

	public static final int ACTION_MESSAGE_STATE_SUCCESS = 0;
	public static final int ACTION_MESSAGE_STATE_FAILURE = 1;

	private int state = ACTION_MESSAGE_STATE_SUCCESS;

	private List<String> messages;

	private String data;

	public boolean state() {
		if (state == ACTION_MESSAGE_STATE_SUCCESS)
			return true;
		return false;

	}

	public ActionMessage success() {
		state = ACTION_MESSAGE_STATE_SUCCESS;
		return this;
	}

	public ActionMessage success(String msg) {
		return addMessage(msg).success();
	}

	public ActionMessage failure() {
		state = ACTION_MESSAGE_STATE_FAILURE;
		return this;
	}
	
	public ActionMessage failure(String msg) {
		return addMessage(msg).failure();
	}

	public static ActionMessage instance() {
		return new ActionMessage();
	}

	public void clear() {
		this.state = ACTION_MESSAGE_STATE_SUCCESS;
		this.data = null;
		if (messages != null) {
			messages.clear();
		}
	}

	public ActionMessage addErrorMessage(String msg) {
		return addMessage(msg).failure();
	}

	public ActionMessage addMessage(String msg) {
		if (messages == null) {
			messages = new ArrayList<String>();
		}
		messages.add(msg);
		return this;
	}

	public ActionMessage setData(String data) {
		this.data = data;
		return this;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSONObject() throws DIJSONException {
		try {
			JSONObject jo = new JSONObject();
			jo.put("state", this.state);
			if (this.data != null)
				jo.put("data", this.data);
			if (messages != null)
				jo.put("messages", JSONUtils.convertToJSONArray(messages));
			return jo;
		} catch (Exception e) {
			throw new DIJSONException("获得活动消息的JSON字符串出现错误", e);
		}
	}

	public String toJSONString() throws DIJSONException {
		return toJSONObject().toJSONString();
	}

}
