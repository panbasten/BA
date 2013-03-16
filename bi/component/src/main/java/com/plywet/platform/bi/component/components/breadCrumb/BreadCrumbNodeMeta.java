package com.plywet.platform.bi.component.components.breadCrumb;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.plywet.platform.bi.component.components.SimpleComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIJSONException;

public class BreadCrumbNodeMeta extends SimpleComponentMeta implements
		ComponentMetaInterface {

	public static final String ATTR_PATH = "path";
	public static final String ATTR_DISPLAY_NAME = "displayName";

	private String path;

	public static final String[] ATTRS = new String[] { HTML.ATTR_ID,
			ATTR_DISPLAY_NAME, HTML.ATTR_TITLE, HTML.ATTR_SRC };

	public BreadCrumbNodeMeta() {
	}

	public BreadCrumbNodeMeta(String id, String displayName, String src,
			String path) throws BIJSONException {
		super();
		super.setId(id);
		super.addAttribute(ATTR_DISPLAY_NAME, displayName);
		super.addAttribute(HTML.ATTR_TITLE, displayName);
		super.addAttribute(HTML.ATTR_SRC, src);
		this.path = path;
	}

	@Override
	public String[] getAttributesName() {
		return ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BREAD_CRUMB_NODE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormJo() throws BIJSONException {
		Map data;

		if (super.getData() == null || !(super.getData() instanceof Map)) {
			data = new HashMap();
		} else {
			data = (Map) super.getData();
		}
		data.put(HTML.ATTR_ID, getId());
		data.put(ATTR_PATH, path);

		super.setData(data);

		return super.getFormJo();
	}

	public String getPath() {
		return path;
	}

	public BreadCrumbNodeMeta setPath(String path) {
		this.path = path;
		return this;
	}

}
