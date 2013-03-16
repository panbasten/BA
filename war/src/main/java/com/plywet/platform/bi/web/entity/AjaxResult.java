package com.plywet.platform.bi.web.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.core.utils.Utils;

public class AjaxResult {
	private List<AjaxResultEntity> entitys = new ArrayList<AjaxResultEntity>();

	public static AjaxResult instance() {
		return new AjaxResult();
	}

	public static AjaxResult instanceDialogContent(String targetId,
			Object[] domAndScript) {
		AjaxResult ar = new AjaxResult();
		// 1.清空内容区
		AjaxResultEntity empty = AjaxResultEntity.instance().setOperation(
				Utils.RESULT_OPERATION_EMPTY).setTargetId(targetId);

		// 2.添加内容页
		AjaxResultEntity content = AjaxResultEntity.instance().setOperation(
				Utils.RESULT_OPERATION_APPEND).setTargetId(targetId)
				.setDomAndScript(domAndScript);
		return ar.addEntity(empty).addEntity(content);
	}

	public List<AjaxResultEntity> getEntitys() {
		return entitys;
	}

	public void setEntitys(List<AjaxResultEntity> entitys) {
		this.entitys = entitys;
	}

	public AjaxResult addEntity(AjaxResultEntity entity) {
		this.entitys.add(entity);
		return this;
	}

	@SuppressWarnings("unchecked")
	public Object toJSON() throws BIJSONException {
		JSONArray ja = new JSONArray();
		for (AjaxResultEntity r : this.entitys) {
			ja.add(r.toJSON());
		}
		return ja;
	}

	public String toJSONString() throws BIJSONException {
		return ((JSONArray) this.toJSON()).toJSONString();
	}
}
