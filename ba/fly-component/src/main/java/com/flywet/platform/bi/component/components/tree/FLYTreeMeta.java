package com.flywet.platform.bi.component.components.tree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.ComplexComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class FLYTreeMeta extends ComplexComponentMeta implements
		ComponentMetaInterface {

	public static final String ATTR_DATA = "data";

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_TREE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormJo() throws BIJSONException {
		JSONObject formJo = super.getAttrbuteJo();
		formJo.put(HTML.ATTR_ID, this.getId());
		if (this.getContents() != null && this.getContents().size() > 0) {
			formJo.put(ATTR_DATA, getDataFormJa());
		}
		return formJo;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getDataFormJa() throws BIJSONException {
		JSONArray sub = new JSONArray();
		for (ComponentMetaInterface dataMeta : this.getContents()) {
			if (dataMeta != null) {
				if (dataMeta instanceof ComplexComponentMeta) {
					if (((ComplexComponentMeta) dataMeta).isMultiRoot()) {
						sub.addAll(((ComplexComponentMeta) dataMeta)
								.getFormJa());
					} else {
						sub.add(dataMeta.getFormJo());
					}
				} else {
					sub.add(dataMeta.getFormJo());
				}
			}
		}
		return sub;
	}

}
