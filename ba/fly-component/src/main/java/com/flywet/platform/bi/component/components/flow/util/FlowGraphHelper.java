package com.flywet.platform.bi.component.components.flow.util;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.flow.FlowChartData;
import com.flywet.platform.bi.component.components.flow.FlowDefaultAttributes;
import com.flywet.platform.bi.component.components.flow.FlowElementSet;
import com.flywet.platform.bi.component.components.flow.FlowHop;
import com.flywet.platform.bi.component.components.flow.FlowStep;
import com.flywet.platform.bi.component.components.flow.step.DefaultStep;
import com.flywet.platform.bi.component.components.flow.step.SysStepType;
import com.flywet.platform.bi.core.exception.BIJSONException;

/**
 * 用于生成Flow图形对象
 * 
 * @author 潘巍（Peter Pan）
 * @since 2011-2-11 下午06:00:06
 */
public class FlowGraphHelper {
	private final static Logger log = Logger.getLogger(FlowGraphHelper.class);

	/**
	 * 转换JSON对象为FlowChartModel对象
	 * 
	 * @param json
	 * @return
	 */
	public static FlowChartData convertJsonToFlowChartDataMeta(JSONObject json) {
		FlowChartData co = null;
		try {
			if (json != null) {
				co = createFlowChartDataMeta();
				co.construct(json);
				return co;
			}
		} catch (Exception e) {
			log.error("由json对象转换FlowChartModel对象出现错误.");
		}
		return null;
	}

	/**
	 * 创建流程图形模型(FlowChartModel)对象
	 * 
	 * @return
	 */
	public static FlowChartData createFlowChartDataMeta() {
		try {
			FlowChartData co = new FlowChartData();
			co.setElementSet(new FlowElementSet());
			co.setDefaultAttributes(new FlowDefaultAttributes());
			return co;
		} catch (Exception e) {
			log.error("创建FlowChartModel对象出现错误.");
		}
		return null;
	}

	/**
	 * 创建线条(FlowHop)对象
	 * 
	 * @return
	 */
	public static FlowHop createHop() {
		return new FlowHop();
	}

	/**
	 * 创建图形(FlowStep)对象
	 * 
	 * @param st
	 * @return
	 */
	public static FlowStep createStep(String type) {
		try {
			if (type != null) {
				if (type.startsWith(FlowStep.SYS_FLOW_STEP_TYPE_PREFIX)) {
					return SysStepType.get(
							type.substring(FlowStep.SYS_FLOW_STEP_TYPE_PREFIX
									.length())).createStep();
				} else {
					Object obj = Class.forName(type).newInstance();
					if (obj instanceof FlowStep) {
						return (FlowStep) obj;
					}
				}
			}
		} catch (Exception e) {
			log.error("由类型创建FlowStep对象出现错误.");
		}
		return null;

	}

	public static FlowStep createDefaultStep() {
		return new DefaultStep();
	}

	public static final long[] convertJSONArrayToLongArray(JSONArray ja)
			throws BIJSONException {
		int n = ja.size();
		long[] rtn = new long[n];
		for (int i = 0; i < n; i++) {
			rtn[i] = (Long) ja.get(i);
		}
		return rtn;
	}

}
