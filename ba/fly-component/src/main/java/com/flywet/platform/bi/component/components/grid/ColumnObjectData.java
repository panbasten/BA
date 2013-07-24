package com.flywet.platform.bi.component.components.grid;

import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentDataInterface;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class ColumnObjectData implements ComponentDataInterface {

	public static final String ATTR_FIELD = "field";
	public static final String ATTR_WIDTH = "width";
	public static final String ATTR_TITLE = "title";
	public static final String ATTR_ALIGN = "align";
	public static final String ATTR_SORTABLE = "sortable";
	public static final String ATTR_FORMATTER = "formatter";
	public static final String ATTR_EDITOR = "editor";

	private String field;

	private Integer width;

	private String title;

	private String align;

	private Boolean sortable;

	private String formatter;

	private EditorObjectData editor;

	public static ColumnObjectData instance() {
		return new ColumnObjectData();
	}

	@Override
	public void construct(JSONObject json) throws BIJSONException {
		this.field = (String) json.get(ATTR_FIELD);
		this.width = (Integer) json.get(ATTR_WIDTH);
		this.title = (String) json.get(ATTR_TITLE);
		this.align = (String) json.get(ATTR_ALIGN);
		this.sortable = (Boolean) json.get(ATTR_SORTABLE);
		this.formatter = (String) json.get(ATTR_FORMATTER);
		if (json.containsKey(ATTR_EDITOR)) {
			if (this.editor == null) {
				this.editor = new EditorObjectData();
			}
			this.editor.construct((JSONObject) json.get(ATTR_EDITOR));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws BIJSONException {
		JSONObject jo = new JSONObject();
		if (this.field != null)
			jo.put(ATTR_FIELD, this.field);
		if (this.width != null)
			jo.put(ATTR_WIDTH, this.width);
		if (this.title != null)
			jo.put(ATTR_TITLE, this.title);
		if (this.align != null)
			jo.put(ATTR_ALIGN, this.align);
		if (this.sortable != null)
			jo.put(ATTR_SORTABLE, this.sortable);
		if (this.formatter != null)
			jo.put(ATTR_FORMATTER, this.formatter);
		if (this.editor != null)
			jo.put(ATTR_EDITOR, this.editor.getFormDataJo());
		return null;
	}

	@Override
	public void removeAll() throws BIJSONException {
		this.field = null;
		this.width = null;
		this.title = null;
		this.align = null;
		this.sortable = null;
		this.formatter = null;
		this.editor = null;
	}

	public String getField() {
		return field;
	}

	public ColumnObjectData setField(String field) {
		this.field = field;
		return this;
	}

	public Integer getWidth() {
		return width;
	}

	public ColumnObjectData setWidth(Integer width) {
		this.width = width;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public ColumnObjectData setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getAlign() {
		return align;
	}

	public ColumnObjectData setAlign(String align) {
		this.align = align;
		return this;
	}

	public Boolean getSortable() {
		return sortable;
	}

	public ColumnObjectData setSortable(Boolean sortable) {
		this.sortable = sortable;
		return this;
	}

	public String getFormatter() {
		return formatter;
	}

	public ColumnObjectData setFormatter(String formatter) {
		this.formatter = formatter;
		return this;
	}

	public EditorObjectData getEditor() {
		return editor;
	}

	public ColumnObjectData setEditor(EditorObjectData editor) {
		this.editor = editor;
		return this;
	}

}
