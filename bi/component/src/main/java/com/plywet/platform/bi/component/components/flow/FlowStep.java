package com.plywet.platform.bi.component.components.flow;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.plywet.platform.bi.core.exception.BIJSONException;
import com.plywet.platform.bi.core.utils.ArrayUtils;

public abstract class FlowStep extends AbstractFlowGraph {

	public static final String SYS_FLOW_STEP_TYPE_PREFIX = "sys:step:";

	// 画图类型
	public static final String TYPE = "type";

	// 在画布的X坐标
	public static final String D_X = "dx";
	// 在画布的Y坐标
	public static final String D_Y = "dy";
	// 在画布的显示区域宽度
	public static final String D_WIDTH = "dWidth";
	// 在画布的显示区域高度
	public static final String D_HEIGHT = "dHeight";
	// 下方的文字
	public static final String B_TEXT = "bText";
	// 下方文字的样式
	public static final String B_TEXT_STYLE = "bTextStyle";
	// 左上角的文字
	public static final String TL_TEXT = "tlText";
	// 左上角文字的样式
	public static final String TL_TEXT_STYLE = "tlTextStyle";
	// 中间的文字
	public static final String C_TEXT = "cText";
	// 中间文字的样式
	public static final String C_TEXT_STYLE = "cTextStyle";
	// 提供的类型
	public static final String PROVIDER = "provider";
	// 接受的类型集合
	public static final String ACCEPT = "accept";
	public static final String ACCEPT_ALL = "acceptAll";
	// 在该节点开始连线的判断方法
	public static final String ON_START_HOP = "onStartHop";
	// 在该节点结束连线的判断方法
	public static final String ON_END_HOP = "onEndHop";
	// 在该节点接受连线的方法
	public static final String ON_ACCEPT_HOP = "onAcceptHop";

	// 是否可以改变大小
	public static final String CHANGABLE = "changable";

	public static final String[] STEP_ATTRIBUTE_INT = new String[] { D_X, D_Y,
			D_WIDTH, D_HEIGHT };

	public static final String[] STEP_ATTRIBUTE_STRING = new String[] {
			B_TEXT_STYLE, TL_TEXT_STYLE, C_TEXT_STYLE, PROVIDER, ON_START_HOP,
			ON_END_HOP, ON_ACCEPT_HOP };

	public static final String[] STEP_ATTRIBUTE_STRING_ARRAY = new String[] {
			B_TEXT, TL_TEXT, C_TEXT, ACCEPT };

	public static final String[] STEP_ATTRIBUTE_BOOLEAN = new String[] {
			ACCEPT_ALL, CHANGABLE };

	public static final String[] STEP_ATTRIBUTE = ArrayUtils.concat(
			STEP_ATTRIBUTE_INT, STEP_ATTRIBUTE_STRING, STEP_ATTRIBUTE_BOOLEAN,
			new String[] { TYPE });

	public abstract String getType();

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws BIJSONException {
		try {
			// 添加父类属性
			JSONObject jo = super.getFormDataJo();

			// 添加Step类的属性
			for (String attr : STEP_ATTRIBUTE_INT) {
				if (this.hasAttribute(attr)) {
					jo.put(attr, this.getIntAttribute(attr));
				}
			}

			for (String attr : STEP_ATTRIBUTE_STRING) {
				if (this.hasAttribute(attr))
					jo.put(attr, this.getStringAttribute(attr));
			}

			for (String attr : STEP_ATTRIBUTE_STRING_ARRAY) {
				if (this.hasAttribute(attr)) {
					jo.put(attr, this.getStringArrayAttribute(attr));
				}
			}

			for (String attr : STEP_ATTRIBUTE_BOOLEAN) {
				if (this.hasAttribute(attr)) {
					jo.put(attr, this.getBooleanAttribute(attr));
				}
			}

			// 添加类型
			jo.put(TYPE, this.getType());

			return jo;
		} catch (Exception e) {
			throw new BIJSONException("将该对象转换为json对象时出现错误.", e);
		}
	}

	@Override
	public void construct(JSONObject json) throws BIJSONException {
		try {
			super.construct(json);

			for (String attr : STEP_ATTRIBUTE_INT) {
				if (json.containsKey(attr))
					addAttribute(attr, String.valueOf(json.get(attr)));
			}

			for (String attr : STEP_ATTRIBUTE_STRING) {
				if (json.containsKey(attr))
					addAttribute(attr, (String) json.get(attr));
			}

			for (String attr : STEP_ATTRIBUTE_STRING_ARRAY) {
				if (json.containsKey(attr)) {
					Object obj = json.get(attr);
					if (obj instanceof String) {
						addAttribute(attr, (String) obj);
					} else if (obj instanceof JSONArray) {
						String a = "";
						for (int i = 0; i < ((JSONArray) obj).size(); i++) {
							a += ",";
							a += ((JSONArray) obj).get(i);
						}
						if (!"".equals(a)) {
							addAttribute(attr, a.substring(1));
						} else {
							addAttribute(attr, "");
						}
					}
				}
			}

			for (String attr : STEP_ATTRIBUTE_BOOLEAN) {
				if (json.containsKey(attr))
					addAttribute(attr, String.valueOf(json.get(attr)));
			}

		} catch (Exception e) {
			throw new BIJSONException("由json对象构造该对象时出现错误.", e);
		}
	}

}
