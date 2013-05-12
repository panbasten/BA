package com.plywet.platform.bi.component.components.tree;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.plywet.platform.bi.component.components.BaseComponentMeta;
import com.plywet.platform.bi.component.core.ComponentMetaInterface;
import com.plywet.platform.bi.component.utils.HTML;
import com.plywet.platform.bi.core.exception.BIJSONException;

public class FLYTreeMeta extends BaseComponentMeta implements
		ComponentMetaInterface {

	private List<TreeNode> subNode;

	private TreeNode root;

	public FLYTreeMeta() {
	}

	public FLYTreeMeta(TreeNode root) {
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
	public JSONObject getFormJo() throws BIJSONException {
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
