package com.yonyou.bq8.di.component.components.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.BaseComponentMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

public class TreeNode extends BaseComponentMeta implements
		ComponentMetaInterface {

	/**
	 * 子节点
	 */
	private List<TreeNode> subNode;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 文字
	 */
	private String text;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 节点是否默认关闭
	 */
	private boolean closed = true;

	/**
	 * 单击事件
	 */
	private String onClick;

	/**
	 * 拖拽事件
	 */
	private String onDrag;

	/**
	 * 是否叶子
	 */
	private Boolean leaf;

	/**
	 * 判断是否是叶子节点
	 * 
	 * @return
	 */
	public boolean isLeaf() {
		if (leaf == null) {
			if (subNode != null && subNode.size() > 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return this.leaf;
		}
	}

	@SuppressWarnings("unchecked")
	public void addData(String key, String value) {
		if (this.getData() == null) {
			this.setData(new HashMap<String, String>());
		}
		((Map) this.getData()).put(key, value);
	}

	public void addSubNode(TreeNode node) {
		if (subNode == null) {
			subNode = new ArrayList<TreeNode>();
		}
		subNode.add(node);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getOnDrag() {
		return onDrag;
	}

	public void setOnDrag(String onDrag) {
		this.onDrag = onDrag;
	}

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_TREE_NODE;
	}

	@Override
	public JSONObject getFormJo() throws DIJSONException {
		if (this.type != null)
			super.addExtendAttribute(HTML.ATTR_TYPE, this.type);
		if (this.text != null)
			super.addExtendAttribute("text", this.text);
		if (this.desc != null)
			super.addExtendAttribute("desc", this.desc);
		if (this.icon != null)
			super.addExtendAttribute("icon", this.icon);
		super.addExtendAttribute("closed", this.closed);
		if (this.onClick != null)
			super.addExtendAttribute(HTML.ATTR_ON_CLICK, this.onClick);
		if (this.onDrag != null)
			super.addExtendAttribute(HTML.ATTR_ON_DRAG, this.onDrag);
		return super.getFormJo();
	}

}
