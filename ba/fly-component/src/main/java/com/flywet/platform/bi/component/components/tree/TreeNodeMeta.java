package com.flywet.platform.bi.component.components.tree;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flywet.platform.bi.component.components.ComplexComponentMeta;
import com.flywet.platform.bi.component.core.ComponentMetaInterface;
import com.flywet.platform.bi.component.utils.HTML;
import com.flywet.platform.bi.core.exception.BIJSONException;

public class TreeNodeMeta extends ComplexComponentMeta implements
		ComponentMetaInterface {

	// 子节点
	public static final String ATTR_CHILDREN = "children";

	// 文字
	public static final String ATTR_TEXT = "text";

	// 描述
	public static final String ATTR_DESC = "desc";

	// 图标
	public static final String ATTR_ICON_CLS = "iconCls";

	// 节点是否默认关闭
	public static final String ATTR_CLOSED = "closed";

	// 是否叶子
	public static final String ATTR_LEAF = "leaf";

	/**
	 * 判断是否是叶子节点
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		Boolean leaf = (Boolean) this.getAttribute(ATTR_LEAF);
		if (leaf == null) {
			if (this.getContents() != null && this.getContents().size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return leaf;
		}
	}

	public TreeNodeMeta setLeaf(boolean val) throws BIJSONException {
		this.addAttribute(ATTR_LEAF, val);
		return this;
	}

	@SuppressWarnings("unchecked")
	public void addData(String key, String value) {
		if (this.getData() == null) {
			this.setData(new HashMap<String, String>());
		}
		((Map) this.getData()).put(key, value);
	}

	public String getType() {
		return (String) this.getAttribute(HTML.ATTR_TYPE);
	}

	public TreeNodeMeta setType(String val) throws BIJSONException {
		this.addAttribute(HTML.ATTR_TYPE, val);
		return this;
	}

	public String getText() {
		return (String) this.getAttribute(ATTR_TEXT);
	}

	public TreeNodeMeta setText(String val) throws BIJSONException {
		this.addAttribute(HTML.ATTR_TEXT, val);
		return this;
	}

	public String getDesc() {
		return (String) this.getAttribute(ATTR_DESC);
	}

	public TreeNodeMeta setDesc(String val) throws BIJSONException {
		this.addAttribute(ATTR_DESC, val);
		return this;
	}

	public String getIconCls() {
		return (String) this.getAttribute(ATTR_ICON_CLS);
	}

	public TreeNodeMeta setIcon(String val) throws BIJSONException {
		this.addAttribute(ATTR_ICON_CLS, val);
		return this;
	}

	public boolean isClosed() {
		return (Boolean) this.getAttribute(ATTR_CLOSED);
	}

	public TreeNodeMeta setClosed(boolean val) throws BIJSONException {
		this.addAttribute(ATTR_CLOSED, val);
		return this;
	}

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_TREE_NODE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormJo() throws BIJSONException {
		JSONObject formJo = super.getAttrbuteJo();
		formJo.put(HTML.ATTR_ID, this.getId());
		if (this.getContents() != null && this.getContents().size() > 0) {
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
			formJo.put(ATTR_CHILDREN, sub);
		}
		return formJo;
	}

}
