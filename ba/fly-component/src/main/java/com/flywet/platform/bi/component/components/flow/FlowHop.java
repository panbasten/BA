package com.flywet.platform.bi.component.components.flow;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.flow.util.FlowGraphHelper;
import com.flywet.platform.bi.component.core.ComponentDataInterface;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.utils.ArrayUtils;

/**
 * 线条对象
 * 
 * @author 潘巍（Peter Pan）
 * @since 2011-2-11 上午11:08:49
 */
public class FlowHop extends AbstractFlowGraph implements
		ComponentDataInterface {

	// 开始元素
	public static final String FROM_EL_ID = "fromElId";
	// 结束元素
	public static final String TO_EL_ID = "toElId";
	// 连线的所有点的x坐标
	public static final String X = "x";
	// 连线的所有点的y坐标
	public static final String Y = "y";
	// 连线的样式
	public static final String STYLE = "style";
	// 文字
	public static final String TEXT = "text";
	// 文字样式
	public static final String TEXT_STYLE = "textStyle";
	// 开始箭头类型
	public static final String ARROW_START_TYPE = "arrowStartType";
	// 结束箭头类型
	public static final String ARROW_END_TYPE = "arrowEndType";
	// 线条类型
	public static final String LINE_TYPE = "lineType";
	// 开始文字
	public static final String START_TEXT = "startText";
	// 结束箭头类型
	public static final String END_TEXT = "endText";
	// 开始箭头绘图方法
	public static final String ARROW_START_DRAW_FUNCTION = "arrowStartDrawFunction";
	// 结束箭头绘图方法
	public static final String ARROW_END_DRAW_FUNCTION = "arrowEndDrawFunction";
	// 线条绘图方法
	public static final String LINE_DRAW_FUNCTION = "lineDrawFunction";

	public static final String[] HOP_ATTRIBUTE_STRING = new String[] { STYLE,
			TEXT_STYLE, ARROW_START_TYPE, ARROW_END_TYPE, LINE_TYPE,
			ARROW_START_DRAW_FUNCTION, ARROW_END_DRAW_FUNCTION,
			LINE_DRAW_FUNCTION };

	public static final String[] STEP_ATTRIBUTE_STRING_ARRAY = new String[] {
			TEXT, START_TEXT, END_TEXT };

	public static final String[] HOP_ATTRIBUTE = ArrayUtils.concat(
			HOP_ATTRIBUTE_STRING, new String[] { FROM_EL_ID, TO_EL_ID, X, Y });

	/**
	 * 连线的所有点的x坐标
	 */
	private long[] x;

	/**
	 * 连线的所有点的y坐标
	 */
	private long[] y;

	/**
	 * 开始元素
	 */
	private FlowStep fromEl;

	/**
	 * 结束元素
	 */
	private FlowStep toEl;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws BIJSONException {
		try {
			// 添加父类属性
			if (this.getId() == null) {
				createId();
			}

			JSONObject jo = super.getFormDataJo();

			if (this.x != null) {
				JSONArray x = new JSONArray();
				for (long i : this.x) {
					x.add(i);
				}
				jo.put(X, x);
			}

			if (this.y != null) {
				JSONArray y = new JSONArray();
				for (long i : this.y) {
					y.add(i);
				}
				jo.put(Y, y);
			}

			if (this.fromEl != null && this.fromEl.getId() != null)
				jo.put(FROM_EL_ID, this.fromEl.getId());

			if (this.toEl != null && this.toEl.getId() != null)
				jo.put(TO_EL_ID, this.toEl.getId());

			for (String attr : HOP_ATTRIBUTE_STRING) {
				if (this.getAttribute(attr) != null)
					jo.put(attr, this.getAttribute(attr));
			}

			for (String attr : STEP_ATTRIBUTE_STRING_ARRAY) {
				if (this.hasAttribute(attr)) {
					jo.put(attr, this.getStringArrayAttribute(attr));
				}
			}

			return jo;
		} catch (Exception e) {
			throw new BIJSONException("将该对象转换为json对象时出现错误.", e);
		}
	}

	public void createId() {
		String tempId = "";
		if (this.fromEl != null && this.fromEl.getId() != null) {
			tempId += this.fromEl.getId();
		}
		tempId += "->";
		if (this.toEl != null && this.toEl.getId() != null) {
			tempId += this.toEl.getId();
		}
		if (!"->".equals(tempId)) {
			this.setId(tempId);
		}
	}

	@Override
	public void removeAll() {
		super.removeAll();

		this.x = null;
		this.y = null;
		this.fromEl = null;
		this.toEl = null;
	}

	@Override
	public void construct(JSONObject json) throws BIJSONException {
		try {
			super.construct(json);

			if (json.containsKey(X))
				this.x = FlowGraphHelper
						.convertJSONArrayToLongArray((JSONArray) json.get(X));

			if (json.containsKey(Y))
				this.y = FlowGraphHelper
						.convertJSONArrayToLongArray((JSONArray) json.get(Y));

			for (String attr : HOP_ATTRIBUTE_STRING) {
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

		} catch (BIJSONException e) {
			throw new BIJSONException("由json对象构造该对象时出现错误.", e);
		}
	}

	public long[] getX() {
		return x;
	}

	public void setX(long[] x) {
		this.x = x;
	}

	public long[] getY() {
		return y;
	}

	public void setY(long[] y) {
		this.y = y;
	}

	public FlowStep getFromEl() {
		return fromEl;
	}

	public void setFromEl(FlowStep fromEl) {
		this.fromEl = fromEl;
	}

	public FlowStep getToEl() {
		return toEl;
	}

	public void setToEl(FlowStep toEl) {
		this.toEl = toEl;
	}

}
