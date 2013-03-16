package com.plywet.platform.bi.component.components.grid;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;

import com.plywet.platform.bi.component.components.BaseComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIJSONException;

public class FLYGridMeta extends BaseComponentMeta implements
		ComponentMetaInterface {

	private List<List<ColumnObjectData>> columns;

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_GRID;
	}

	public void addColumn(int rowIdx, ColumnObjectData col) {
		if (this.columns == null) {
			this.columns = new ArrayList<List<ColumnObjectData>>();
		}
		List<ColumnObjectData> cols = this.columns.get(rowIdx);
		if (cols == null) {
			cols = new ArrayList<ColumnObjectData>();
			this.columns.add(rowIdx, cols);
		}
		cols.add(col);

	}

	@SuppressWarnings("unchecked")
	public JSONArray getColumns() throws BIJSONException {
		if (columns != null) {
			JSONArray ja = new JSONArray();
			for (List<ColumnObjectData> cols : columns) {
				JSONArray ja2 = new JSONArray();
				for (ColumnObjectData col : cols) {
					ja2.add(col.getFormDataJo());
				}
				ja.add(ja2);
			}
			return ja;
		}
		return null;
	}

}
