package com.flywet.platform.bi.component.components.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.core.ComponentDataInterface;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.utils.JSONUtils;

public class GridDataObject implements ComponentDataInterface {

	public static final String ATTR_TOTAL = "total";
	public static final String ATTR_ROWS = "rows";

	private String[] paramNames;

	private int minRows = 0;

	private Object objects;

	private boolean transpose;

	public static GridDataObject instance() {
		return new GridDataObject();
	}

	public static GridDataObject instance(Properties prop) {
		GridDataObject obj = new GridDataObject();
		obj.putProperties(prop);
		return obj;
	}

	public GridDataObject transpose() {
		this.transpose = true;
		return this;
	}

	@SuppressWarnings("unchecked")
	public GridDataObject putObject(Object obj) {
		if (this.objects == null) {
			this.objects = new ArrayList();
		}
		((List) this.objects).add(obj);
		return this;
	}

	@SuppressWarnings("unchecked")
	public GridDataObject putObjects(Object[] objs) {

		if (this.objects == null) {
			this.objects = new ArrayList();
		}
		if (objs != null) {
			for (Object obj : objs) {
				((List) this.objects).add(obj);
			}
		}
		return this;
	}

	public GridDataObject putObjects(String[] paramNames, Object[][] objs) {
		this.paramNames = paramNames;
		this.objects = objs;
		return this;
	}

	@SuppressWarnings("unchecked")
	public GridDataObject putObjects(List objs) {
		this.objects = objs;
		return this;
	}

	public GridDataObject putProperties(Properties prop) {
		this.objects = prop;
		return this;
	}

	public int getMinRows() {
		return minRows;
	}

	public GridDataObject setMinRows(int minRows) {
		this.minRows = minRows;
		return this;
	}

	@Override
	public void construct(JSONObject json) throws BIJSONException {

	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormDataJo() throws BIJSONException {
		JSONObject jo = new JSONObject();
		JSONArray rowsja = null;
		if (objects != null) {
			if (objects instanceof List) {
				rowsja = new JSONArray();
				List objs = (List) objects;
				for (Object obj : objs) {
					rowsja.add(JSONUtils.convertToJSONObject(obj));
				}
			} else if (objects instanceof Object[][]) {
				rowsja = new JSONArray();
				Object[][] objs = (Object[][]) objects;
				// 如果是转置
				if (transpose) {
					int i = objs.length, j = objs[0].length;
					for (int ii = 0; ii < j; ii++) {
						JSONObject rowjo = new JSONObject();
						for (int jj = 0; jj < i; jj++) {
							rowjo.put(paramNames[jj], JSONUtils
									.convert(objs[jj][ii]));
						}
						rowsja.add(rowjo);
					}
				}
				// 如果是正常
				else {
					for (Object[] obj : objs) {
						rowsja.add(JSONUtils.convertToJSONObject(obj,
								paramNames));
					}
				}
			} else if (objects instanceof Properties) {
				rowsja = JSONUtils.convertToJSONArray((Properties) objects);
			}
		}
		if (rowsja == null) {
			rowsja = new JSONArray();
		}
		for (int i = 0, rest = this.minRows - rowsja.size(); i < rest; i++) {
			rowsja.add(new JSONObject());
		}
		jo.put(ATTR_TOTAL, rowsja.size());
		jo.put(ATTR_ROWS, rowsja);
		return jo;
	}

	@Override
	public void removeAll() throws BIJSONException {
		this.minRows = 0;
		this.paramNames = null;
		this.objects = null;
	}

}
