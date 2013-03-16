package com.plywet.platform.bi.component.components.browse;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.plywet.platform.bi.component.components.SimpleComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIJSONException;

public class BrowseNodeMeta extends SimpleComponentMeta implements
		ComponentMetaInterface {

	public static final String ATTR_CATEGORY = "category";
	public static final String ATTR_PATH = "path";
	public static final String ATTR_DISPLAY_NAME = "displayName";
	public static final String ATTR_ICON_STYLE = "iconStyle";

	public static final String ATTR_TYPE_ROOT = "root";
	public static final String ATTR_TYPE_NODE = "node";
	public static final String ATTR_TYPE_LEAF = "leaf";

	public static final String[] ATTRS = new String[] { HTML.ATTR_ID,
			HTML.ATTR_NAME, ATTR_DISPLAY_NAME, HTML.ATTR_TITLE, HTML.ATTR_SRC,
			HTML.ATTR_ICON, ATTR_ICON_STYLE, HTML.ATTR_TYPE };

	@Override
	public String[] getAttributesName() {
		return ATTRS;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_BROWSE_NODE;
	}

	/**
	 * 所属类别
	 */
	private String category;

	/**
	 * 在系统结构中的完整路径
	 */
	private String path;

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
		data.put(HTML.ATTR_TYPE, super.getAttribute(HTML.ATTR_TYPE));
		data.put(ATTR_CATEGORY, category);
		data.put(ATTR_PATH, path);

		super.setData(data);

		return super.getFormJo();
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
