package com.flywet.platform.bi.web.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.utils.JSONUtils;
import com.flywet.platform.bi.core.utils.Utils;

public class AjaxResultEntity {
	/**
	 * Ajax操作类型
	 */
	private String operation;

	/**
	 * 操作目标对象
	 */
	private String targetId;

	/**
	 * DOM结构
	 */
	private String dom;

	/**
	 * 自定义操作命令
	 */
	private String cmd;

	/**
	 * 自定义执行脚本
	 */
	private List<String> script;

	/**
	 * 自定义数据
	 */
	private Object data;

	// private boolean result;
	// private String msg;

	public static AjaxResultEntity instance() {
		return new AjaxResultEntity();
	}

	public static AjaxResultEntity instanceEmpty(String targetId) {
		AjaxResultEntity emptyEntity = new AjaxResultEntity();
		emptyEntity.setOperation(Utils.RESULT_OPERATION_EMPTY);
		emptyEntity.setTargetId(targetId);
		return emptyEntity;
	}

	public static AjaxResultEntity instanceAppend(String targetId,
			Object[] domString) {
		AjaxResultEntity emptyEntity = new AjaxResultEntity();
		emptyEntity.setOperation(Utils.RESULT_OPERATION_APPEND);
		emptyEntity.setTargetId(targetId);
		emptyEntity.setDomAndScript(domString);
		return emptyEntity;
	}

	public AjaxResultEntity() {
	}

	public AjaxResultEntity(String operation, String targetId, String dom) {
		this.operation = operation;
		this.targetId = targetId;
		this.dom = dom;

	}

	public String getOperation() {
		return operation;
	}

	public AjaxResultEntity setOperation(String operation) {
		this.operation = operation;
		return this;
	}

	public String getTargetId() {
		return targetId;
	}

	public AjaxResultEntity setTargetId(String targetId) {
		this.targetId = targetId;
		return this;
	}

	public String getDom() {
		return dom;
	}

	public AjaxResultEntity setDom(String dom) {
		this.dom = dom;
		return this;
	}

	public String getCmd() {
		return cmd;
	}

	public AjaxResultEntity setCmd(String cmd) {
		this.cmd = cmd;
		return this;
	}

	public Object getData() {
		return data;
	}

	public AjaxResultEntity setData(Object data) {
		this.data = data;
		return this;
	}

	public List<String> getScript() {
		return script;
	}

	public AjaxResultEntity setScript(List<String> script) {
		this.script = script;
		return this;
	}

	public AjaxResultEntity addScript(String script) {
		if (this.script == null) {
			this.script = new ArrayList<String>();
		}
		this.script.add(script);
		return this;
	}

	@SuppressWarnings("unchecked")
	public AjaxResultEntity setDomAndScript(Object[] obj) {
		this.dom = (String) obj[0];
		this.script = (List<String>) obj[1];
		return this;
	}

	@SuppressWarnings("unchecked")
	public Object toJSON() throws BIJSONException {
		JSONObject jo = new JSONObject();
		jo.put("operation", this.operation);
		jo.put("targetId", this.targetId);
		jo.put("dom", this.dom);
		jo.put("cmd", this.cmd);
		jo.put("script", JSONUtils.convertToJSONArray(this.script));
		if (this.data instanceof ComponentMetaInterface) {
			jo.put("data", ((ComponentMetaInterface) this.data).getFormJo());
		} else {
			jo.put("data", this.data);
		}

		return jo;
	}

}
