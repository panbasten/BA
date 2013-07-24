package com.flywet.platform.bi.component.components.flow.step;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.flow.FlowStep;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.utils.ArrayUtils;

public class PictureStep extends FlowStep {

	public static final String TYPE_NAME = "picture";

	// 图片开始的X坐标
	public static final String S_X = "sx";
	// 图片开始的Y坐标
	public static final String S_Y = "sy";
	// 图片取图的宽度
	public static final String S_WIDTH = "sWidth";
	// 图片取图的高度
	public static final String S_HEIGHT = "sHeight";
	// 图片的Src
	public static final String S_IMG_SRC = "imgSrc";
	// 图片的ID
	public static final String S_IMG_ID = "imgId";

	public static final String[] PICTURE_STEP_ATTRIBUTE = ArrayUtils.concat(
			STEP_ATTRIBUTE, new String[] { S_X, S_Y, S_WIDTH, S_HEIGHT,
					S_IMG_SRC, S_IMG_ID });

	@Override
	public String getType() {
		return FlowStep.SYS_FLOW_STEP_TYPE_PREFIX + TYPE_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws BIJSONException {
		try {
			// 添加父类属性
			JSONObject jo = super.getFormDataJo();

			if (this.hasAttribute(S_X))
				jo.put(S_X, this.getIntAttribute(S_X));

			if (this.hasAttribute(S_Y))
				jo.put(S_Y, this.getIntAttribute(S_Y));

			if (this.hasAttribute(S_WIDTH))
				jo.put(S_WIDTH, this.getIntAttribute(S_WIDTH));

			if (this.hasAttribute(S_HEIGHT))
				jo.put(S_HEIGHT, this.getIntAttribute(S_HEIGHT));

			if (this.hasAttribute(S_IMG_SRC))
				jo.put(S_IMG_SRC, this.getStringAttribute(S_IMG_SRC));

			if (this.hasAttribute(S_IMG_ID))
				jo.put(S_IMG_ID, this.getStringAttribute(S_IMG_ID));

			return jo;
		} catch (Exception e) {
			throw new BIJSONException("将该对象转换为json对象时出现错误.", e);
		}
	}

	@Override
	public void construct(JSONObject json) throws BIJSONException {
		try {
			super.construct(json);

			if (json.containsKey(S_X))
				addAttribute(S_X, String.valueOf(json.get(S_X)));

			if (json.containsKey(S_Y))
				addAttribute(S_Y, String.valueOf(json.get(S_Y)));

			if (json.containsKey(S_WIDTH))
				addAttribute(S_WIDTH, String.valueOf(json.get(S_WIDTH)));

			if (json.containsKey(S_HEIGHT))
				addAttribute(S_HEIGHT, String.valueOf(json.get(S_HEIGHT)));

			if (json.containsKey(S_IMG_SRC))
				addAttribute(S_IMG_SRC, (String) json.get(S_IMG_SRC));

			if (json.containsKey(S_IMG_ID))
				addAttribute(S_IMG_ID, (String) json.get(S_IMG_ID));

		} catch (Exception e) {
			throw new BIJSONException("由json对象构造该对象时出现错误.", e);
		}
	}

}
