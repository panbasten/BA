package com.flywet.platform.bi.pivot.service.impl;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.pivot.model.PivotReport;
import com.flywet.platform.bi.pivot.service.intf.BIPivotDelegates;
import com.flywet.platform.bi.pivot.utils.BIContext;

@Service("bi.service.pivotService")
public class BIPivotService implements BIPivotDelegates {

	/**
	 * 执行一个Mdx语句
	 */
	@Override
	public JSONObject query(PivotReport pr) throws BIException {

		try {
			// 获得执行模型
			BIContext context = new BIContext("test");
			// 初始化模型
			pr.init(context);
			JSONObject jo = pr.renderJo(context);
			return jo;
		} catch (BIException e) {
			throw new BIException(e.getMessage(), e);
		} catch (Exception e) {
			throw new BIException("执行MDX查询出现错误。", e);
		}

	}
}
