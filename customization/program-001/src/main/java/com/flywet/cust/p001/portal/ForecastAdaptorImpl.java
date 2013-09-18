package com.flywet.cust.p001.portal;

import org.apache.log4j.Logger;

import com.flywet.platform.bi.component.utils.FLYVariableResolver;
import com.flywet.platform.bi.component.utils.PageTemplateInterpolator;
import com.flywet.platform.bi.component.web.ActionMessage;
import com.flywet.platform.bi.component.web.AjaxResult;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.delegates.model.BIAbstractDbAdaptor;

public class ForecastAdaptorImpl extends BIAbstractDbAdaptor implements
		ForecastAdaptor {
	private final Logger log = Logger.getLogger(ForecastAdaptorImpl.class);

	private static Class<?> PKG = ForecastAdaptorImpl.class;

	private static final String TEMPLATE_MONTH_PREDICT = "monthPredict.h";
	private static final String TEMPLATE_EXTEND_PREDICT = "extendPredict.h";
	private static final String TEMPLATE_MONTH_PREDICT_SCORE = "monthPredictScore.h";
	private static final String TEMPLATE_EXTEND_PREDICT_SCORE = "extendPredictScore.h";

	@Override
	public String monthPredict(String targetId) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_MONTH_PREDICT, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测产品-月预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测产品-月预测界面出现问题。")
				.toJSONString();
	}

	@Override
	public String extendPredict(String targetId) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_EXTEND_PREDICT, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测产品-延伸期预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测产品-延伸期预测界面出现问题。")
				.toJSONString();
	}

	@Override
	public String monthPredictScore(String targetId) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_MONTH_PREDICT_SCORE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测评分-月预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测评分-月预测界面出现问题。")
				.toJSONString();
	}

	@Override
	public String extendPredictScore(String targetId) throws BIJSONException {
		try {
			// 获得页面
			FLYVariableResolver attrsMap = new FLYVariableResolver();

			Object[] domString = PageTemplateInterpolator.interpolate(PKG,
					TEMPLATE_EXTEND_PREDICT_SCORE, attrsMap);

			// 设置响应
			return AjaxResult.instanceDialogContent(targetId, domString)
					.toJSONString();
		} catch (Exception e) {
			log.error("打开预测评分-延伸期预测界面出现问题。");
		}

		return ActionMessage.instance().failure("打开预测评分-延伸期预测界面出现问题。")
				.toJSONString();
	}

}
