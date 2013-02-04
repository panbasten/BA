package com.yonyou.bq8.di.component.components.flow.step;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.flow.FlowStep;
import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.core.utils.ArrayUtils;

public class FigureStep extends FlowStep {

	public static final String[] FIGURE_TYPE_ITEMS = new String[] {
			"Rectangular", "Circle", "Concentric" };

	public static final String TYPE_NAME = "figure";

	public static final String FIGURE_TYPE = "figureType";

	public static final String[] BPM_STEP_ATTRIBUTE = ArrayUtils.concat(
			STEP_ATTRIBUTE, new String[] { FIGURE_TYPE });

	private String figureType;

	@Override
	public String getType() {
		return FlowStep.SYS_FLOW_STEP_TYPE_PREFIX + TYPE_NAME;
	}

	public String getFigureType() {
		return figureType;
	}

	public void setFigureType(String figureType) throws DIJSONException {
		for (String t : FIGURE_TYPE_ITEMS) {
			if (t.equals(figureType)) {
				this.figureType = figureType;
				return;
			}
		}
		throw new DIJSONException("设置图形类型出现错误.");
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws DIJSONException {
		try {
			// 添加父类属性
			JSONObject jo = super.getFormDataJo();

			if (this.hasAttribute(FIGURE_TYPE))
				jo.put(FIGURE_TYPE, this.getAttribute(FIGURE_TYPE));

			return jo;
		} catch (Exception e) {
			throw new DIJSONException("将该对象转换为json对象时出现错误.", e);
		}
	}

	@Override
	public void construct(JSONObject json) throws DIJSONException {
		try {
			super.construct(json);

			if (json.containsKey(FIGURE_TYPE))
				addAttribute(FIGURE_TYPE, (String) json.get(FIGURE_TYPE));

		} catch (Exception e) {
			throw new DIJSONException("由json对象构造该对象时出现错误.", e);
		}
	}

}
