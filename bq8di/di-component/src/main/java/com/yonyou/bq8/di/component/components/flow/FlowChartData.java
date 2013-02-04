package com.yonyou.bq8.di.component.components.flow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.core.ComponentDataInterface;
import com.yonyou.bq8.di.core.exception.DIJSONException;

/**
 * 流程图形对象
 * 
 * @author 潘巍（Peter Pan）
 * @since 2011-2-11 上午11:08:31
 */
public class FlowChartData implements ComponentDataInterface {

	public static final String CANVAS_ELS = "canvasEls";

	public static final String DEFAULT_ATTRIBUTES = "defaultAttributes";

	public static final String ON_CLEAR_ALL = "onClearAll";

	public static final String ON_MODIFY = "onModify";

	/**
	 * 页面元素集合
	 */
	private FlowElementSet elementSet;

	private FlowDefaultAttributes defaultAttributes;

	/**
	 * 清除时调用方法
	 */
	private String onClearAll;

	/**
	 * 发生修改时调用方法
	 */
	private String onModify;

	/**
	 * 扩展的数据
	 */
	private Map<String, Object> extendData;

	public FlowElementSet getElementSet() {
		return elementSet;
	}

	public void setElementSet(FlowElementSet elementSet) {
		this.elementSet = elementSet;
	}

	public FlowDefaultAttributes getDefaultAttributes() {
		return defaultAttributes;
	}

	public void setDefaultAttributes(FlowDefaultAttributes defaultAttributes) {
		this.defaultAttributes = defaultAttributes;
	}

	public String getOnClearAll() {
		return onClearAll;
	}

	public void setOnClearAll(String onClearAll) {
		this.onClearAll = onClearAll;
	}

	public String getOnModify() {
		return onModify;
	}

	public void setOnModify(String onModify) {
		this.onModify = onModify;
	}

	public Map<String, Object> getExtendData() {
		return extendData;
	}

	public void setExtendData(Map<String, Object> extendData) {
		this.extendData = extendData;
	}

	public void addExtendData(String key, String value) {
		if (this.extendData == null) {
			this.extendData = new HashMap<String, Object>();
		}
		this.extendData.put(key, value);
	}

	public void setExtendData(JSONObject jo) throws DIJSONException {
		try {
			if (jo != null) {
				Map<String, Object> rtn = new HashMap<String, Object>();
				String key, value;
				for (Iterator<?> iter = jo.keySet().iterator(); iter.hasNext();) {
					key = (String) iter.next();
					value = jo.get(key).toString();
					rtn.put(key, value);
				}
				this.extendData = rtn;
			}
		} catch (Exception e) {
			throw new DIJSONException("由json对象设置扩展数据出现错误.", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws DIJSONException {
		try {
			JSONObject jo = new JSONObject();
			if (this.elementSet != null)
				jo.put(CANVAS_ELS, this.elementSet.getFormDataJo());
			if (this.defaultAttributes != null)
				jo.put(DEFAULT_ATTRIBUTES, this.defaultAttributes
						.getFormDataJo());

			if (this.onClearAll != null)
				jo.put(ON_CLEAR_ALL, this.onClearAll);

			if (this.onModify != null)
				jo.put(ON_MODIFY, this.onModify);

			if (this.extendData != null) {
				String key;
				JSONObject ed = new JSONObject();
				for (Iterator<String> iter = this.extendData.keySet()
						.iterator(); iter.hasNext();) {
					key = iter.next();
					ed.put(key, this.extendData.get(key));
				}
				jo.put(AbstractFlowGraph.EXTEND_DATA, ed);
			}
			return jo;
		} catch (Exception e) {
			throw new DIJSONException("将该对象转换为json对象时出现错误.", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void construct(JSONObject json) throws DIJSONException {
		try {
			if (json.containsKey(CANVAS_ELS)) {
				if (this.elementSet == null)
					this.elementSet = new FlowElementSet();
				this.elementSet.construct((JSONObject) json.get(CANVAS_ELS));
			}

			if (json.containsKey(DEFAULT_ATTRIBUTES)) {
				if (this.defaultAttributes == null)
					this.defaultAttributes = new FlowDefaultAttributes();
				this.defaultAttributes.construct((JSONObject) json
						.get(DEFAULT_ATTRIBUTES));
			}

			if (json.containsKey(ON_CLEAR_ALL)) {
				this.onClearAll = (String) json.get(ON_CLEAR_ALL);
			}

			if (json.containsKey(ON_MODIFY)) {
				this.onModify = (String) json.get(ON_MODIFY);
			}

			if (json.containsKey(AbstractFlowGraph.EXTEND_DATA))
				this.setExtendData((Map<String, Object>) json
						.get(AbstractFlowGraph.EXTEND_DATA));
		} catch (Exception e) {
			throw new DIJSONException("由json对象构造该对象时出现错误.", e);
		}
	}

	@Override
	public void removeAll() {
		if (this.elementSet != null)
			this.elementSet.removeAll();
		if (this.defaultAttributes != null)
			this.defaultAttributes.removeAll();
		this.onClearAll = null;
		this.onModify = null;
		this.extendData = null;
	}

}
