package com.yonyou.bq8.di.component.components.grid;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.BaseComponentMeta;
import com.yonyou.bq8.di.component.components.calendar.DateBoxMeta;
import com.yonyou.bq8.di.component.components.combo.ComboBoxMeta;
import com.yonyou.bq8.di.component.components.combo.ComboTreeMeta;
import com.yonyou.bq8.di.component.components.number.NumberBoxMeta;
import com.yonyou.bq8.di.component.components.validate.ValidateBoxMeta;
import com.yonyou.bq8.di.component.core.ComponentDataInterface;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;
import com.yonyou.bq8.di.core.utils.JSONUtils;

public class EditorObjectData implements ComponentDataInterface {

	public static final String ATTR_OPTIONS = "options";

	public static final String TYPE_TEXT = "text";
	public static final String TYPE_TEXTAREA = "textarea";
	public static final String TYPE_CHECKBOX = "checkbox";
	public static final String TYPE_NUMBERBOX = "numberbox";
	public static final String TYPE_VALIDATEBOX = "validatebox";
	public static final String TYPE_DATEBOX = "datebox";
	public static final String TYPE_COMBOBOX = "combobox";
	public static final String TYPE_COMBOTREE = "combotree";

	private String type;

	private Map<String, Object> optionsMap;

	private ComponentMetaInterface options;

	public static EditorObjectData instance() {
		return new EditorObjectData();
	}

	public void initText() {
		this.type = TYPE_TEXT;
	}

	public void initTextarea() {
		this.type = TYPE_TEXTAREA;
	}

	public void initCheckbox(String on, String off) {
		this.type = TYPE_CHECKBOX;
		if (on != null)
			this.optionsMap.put("on", on);
		if (off != null)
			this.optionsMap.put("off", off);
	}

	public void initNumberbox(NumberBoxMeta meta) {
		this.type = TYPE_NUMBERBOX;
		this.options = meta;
	}

	public void initvalidatebox(ValidateBoxMeta meta) {
		this.type = TYPE_VALIDATEBOX;
		this.options = meta;
	}

	public void initDatebox(DateBoxMeta meta) {
		this.type = TYPE_DATEBOX;
		this.options = meta;
	}

	public void initCombobox(ComboBoxMeta meta) {
		this.type = TYPE_COMBOBOX;
		this.options = meta;
	}

	public void initCombotree(ComboTreeMeta meta) {
		this.type = TYPE_COMBOTREE;
		this.options = meta;
	}

	public void addOptionMap(String key, Object val) {
		if (this.optionsMap != null) {
			this.optionsMap = new HashMap<String, Object>();
		}
		this.optionsMap.put(key, val);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void construct(JSONObject json) throws DIJSONException {
		if (json != null) {
			if (json.containsKey(HTML.ATTR_TYPE)) {
				this.type = (String) json.get(HTML.ATTR_TYPE);
				JSONObject opt = (JSONObject) json.get(ATTR_OPTIONS);
				if (opt != null) {
					if (TYPE_CHECKBOX.equalsIgnoreCase(this.type)) {
						this.optionsMap = new HashMap<String, Object>();
						this.optionsMap.putAll(opt);
						return;
					}
					BaseComponentMeta meta = null;
					if (TYPE_NUMBERBOX.equalsIgnoreCase(this.type)) {
						meta = new NumberBoxMeta();
					} else if (TYPE_VALIDATEBOX.equalsIgnoreCase(this.type)) {
						meta = new ValidateBoxMeta();
					} else if (TYPE_DATEBOX.equalsIgnoreCase(this.type)) {
						meta = new DateBoxMeta();
					} else if (TYPE_COMBOBOX.equalsIgnoreCase(this.type)) {
						meta = new ComboBoxMeta();
					} else if (TYPE_COMBOTREE.equalsIgnoreCase(this.type)) {
						meta = new ComboTreeMeta();
					}
					String id = (String) opt.remove(HTML.ATTR_ID);
					if (id != null) {
						meta.setId(id);
					}
					Object data = opt.remove(HTML.TAG_DATA);
					if (data != null) {
						meta.setData(data);
					}
					meta.addExtendAttributes(opt);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws DIJSONException {
		if (this.type == null)
			return null;
		JSONObject jo = new JSONObject();
		jo.put(HTML.ATTR_TYPE, this.type);
		if (this.optionsMap != null) {
			jo
					.put(ATTR_OPTIONS, JSONUtils
							.convertToJSONObject(this.optionsMap));
		} else if (this.options != null) {
			JSONObject opts = this.options.getFormJo();
			if (opts != null) {
				JSONObject attrs = new JSONObject();
				if (opts.containsKey(HTML.ATTR_ID)) {
					attrs.put(HTML.ATTR_ID, opts.get(HTML.ATTR_ID));
				}
				if (opts.containsKey(HTML.TAG_DATA)) {
					attrs.put(HTML.TAG_DATA, opts.get(HTML.TAG_DATA));
				}
				if (opts.containsKey(HTML.TAG_ATTRITUDES)) {
					attrs.putAll((JSONObject) opts.get(HTML.TAG_ATTRITUDES));
				}
				jo.put(ATTR_OPTIONS, attrs);
			}

		}
		return jo;
	}

	@Override
	public void removeAll() throws DIJSONException {
		this.type = null;
		this.optionsMap = null;
		this.options = null;
	}

}
