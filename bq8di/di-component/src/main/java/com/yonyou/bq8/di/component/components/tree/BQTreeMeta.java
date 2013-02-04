package com.yonyou.bq8.di.component.components.tree;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.yonyou.bq8.di.component.components.BaseComponentMeta;
import com.yonyou.bq8.di.component.core.ComponentMetaInterface;
import com.yonyou.bq8.di.component.utils.HTML;
import com.yonyou.bq8.di.core.exception.DIJSONException;

public class BQTreeMeta extends BaseComponentMeta implements
		ComponentMetaInterface {

	private List<TreeNode> subNode;

	private TreeNode root;

	public BQTreeMeta() {
	}

	public BQTreeMeta(TreeNode root) {
		this.root = root;
	}

	@Override
	public String[] getAttributesName() {
		return null;
	}

	@Override
	public String getComponentType() {
		return HTML.COMPONENT_TYPE_TREE;
	}

	public void addSubNode(TreeNode node) {
		if (subNode == null) {
			subNode = new ArrayList<TreeNode>();
		}
		subNode.add(node);
	}

	public TreeNode getRoot() {
		return root;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject getFormJo() throws DIJSONException {
		JSONObject jo = super.getFormJo();
		if (root != null)
			jo.put("root", root.getFormJo());
		JSONArray ja = new JSONArray();
		for (TreeNode tn : subNode) {
			ja.add(tn.getFormJo());
		}
		jo.put("subNode", ja);
		return jo;
	}

}
