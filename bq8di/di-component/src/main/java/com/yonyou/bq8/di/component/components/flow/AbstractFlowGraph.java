package com.yonyou.bq8.di.component.components.flow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.core.ComponentDataInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.core.utils.ArrayUtils;
import com.yonyou.bq8.di.core.utils.JSONUtils;

public abstract class AbstractFlowGraph implements ComponentDataInterface {

	public static final String ID = "id";
	public static final String STATE = "state";
	public static final String ON_CLICK = "onClick";
	public static final String ON_CONTEXT_MENU = "onContextMenu";
	public static final String ON_DBLCLICK = "onDblClick";
	public static final String ON_MOVE = "onMove";
	public static final String ON_ROPE = "onRope";

	// 画图的方法
	public static final String CREATE_FUNCTION = "createFunction";

	public static final String[] FLOW_EVENTS = ArrayUtils.concat(
			HTML.COMMON_EVENTS, new String[] { ON_CLICK, ON_CONTEXT_MENU,
					ON_DBLCLICK, ON_MOVE, ON_ROPE, CREATE_FUNCTION });

	public static final String EXTEND_DATA = "extendData";

	/**
	 * ID
	 */
	private String id;

	/**
	 * 状态类型
	 */
	private FlowGraphStateType state;

	/**
	 * 属性
	 */
	private Map<String, Object> attributes;

	/**
	 * 扩展的数据
	 */
	private Map<String, Object> extendData;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public FlowGraphStateType getState() {
		return state;
	}

	public void setState(FlowGraphStateType state) {
		this.state = state;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public Object getAttribute(String key) {
		if (this.attributes != null && key != null) {
			return this.attributes.get(key);
		}
		return null;
	}

	public boolean hasAttribute(String key) {
		if (this.attributes != null && key != null) {
			return this.attributes.containsKey(key);
		}
		return false;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(String key, Object value) {
		if (this.attributes == null) {
			this.attributes = new HashMap<String, Object>();
		}
		this.attributes.put(key, value);
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
			if (this.id != null)
				jo.put(ID, this.id);

			if (this.state != null)
				jo.put(STATE, this.state.getName());

			if (this.attributes != null) {
				for (String e : FLOW_EVENTS) {
					if (this.hasAttribute(e)) {
						jo.put(e, this.attributes.get(e));
					}
				}
			}

			if (this.getCreateFunction() != null) {
				jo.put(CREATE_FUNCTION, this.getCreateFunction());
			}

			if (this.extendData != null) {
				String key;
				JSONObject ed = new JSONObject();
				for (Iterator<String> iter = this.extendData.keySet()
						.iterator(); iter.hasNext();) {
					key = iter.next();
					ed.put(key, this.extendData.get(key));
				}
				jo.put(EXTEND_DATA, ed);
			}
			return jo;
		} catch (Exception e) {
			throw new DIJSONException("将该对象转换为json对象时出现错误.", e);
		}
	}

	@Override
	public void removeAll() {
		this.id = null;
		this.state = null;
		this.attributes = null;
		this.extendData = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void construct(JSONObject json) throws DIJSONException {
		try {
			if (json.containsKey(ID))
				this.id = (String) json.get(ID);

			if (json.containsKey(STATE))
				this.state = FlowGraphStateType.get((String) json.get(STATE));

			for (String e : FLOW_EVENTS) {
				if (json.containsKey(e))
					addAttribute(e, (String) json.get(e));
			}

			if (json.containsKey(EXTEND_DATA))
				this.setExtendData((Map<String, Object>) json.get(EXTEND_DATA));
		} catch (Exception e) {
			throw new DIJSONException("由json对象构造该对象时出现错误.", e);
		}
	}

	public String getCreateFunction() {
		return (String) this.getAttribute(CREATE_FUNCTION);
	}

	public Integer getIntAttribute(String attr) {
		Object a = this.getAttribute(attr);
		if (a instanceof Integer) {
		} else if (a instanceof String) {
			a = Integer.valueOf((String) a);
		} else {
			a = Integer.valueOf(String.valueOf(a));
		}
		return (Integer) a;
	}

	public String getStringAttribute(String attr) {
		return String.valueOf(this.getAttribute(attr));
	}

	public JSONArray getStringArrayAttribute(String attr) {
		Object a = this.getAttribute(attr);
		if (a instanceof String[]) {
			return JSONUtils.convertToJSONArray((String[]) a);
		} else if (a instanceof String) {
			return JSONUtils.convertToJSONArray(new String[] { (String) a });
		} else {
			return JSONUtils.convertToJSONArray(new String[] { String
					.valueOf(a) });
		}
	}

	public Boolean getBooleanAttribute(String attr) {
		Object a = this.getAttribute(attr);
		if (a instanceof Boolean) {
		} else if (a instanceof String) {
			a = Boolean.valueOf((String) a);
		} else {
			a = Boolean.valueOf(String.valueOf(a));
		}
		return (Boolean) a;
	}

}
