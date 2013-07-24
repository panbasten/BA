package com.flywet.platform.bi.report.meta;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.xml.XMLInterface;
import org.pentaho.di.core.xml.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.flywet.platform.bi.report.undo.TemplateAction;
import com.flywet.platform.bi.report.undo.TemplateUndoInterface;

public class TemplateMeta implements XMLInterface, Cloneable,
		TemplateUndoInterface {

	private static Class<?> PKG = TemplateMeta.class;

	public static final String TEMPLATE_ATTRIBUTE_EDITOR_ID = "__editor_id";
	public static final String TEMPLATE_ATTRIBUTE_EDITOR_ID_PREFIX = "dom_";
	public static final String TEMPLATE_ATTRIBUTE_EDITOR_TYPE = "__editor_type";
	public static final String TEMPLATE_ATTRIBUTE_EDITOR_CATEGORY = "__editor_category";
	public static final String TEMPLATE_ATTRIBUTE_EDITOR_INDEX = "__editor_idx";

	protected Document doc;

	protected String templateId;

	protected int idx;

	// 用于支持"undo"操作的活动列表
	protected List<TemplateAction> undo;

	// 最大undo次数
	protected int max_undo = Const.MAX_UNDO;

	// 当前undo位置
	protected int undo_position = -1;

	public TemplateMeta(String templateId, Document doc) {
		this.templateId = templateId;
		this.doc = doc;
		String idxString = XMLUtils.getTagOrAttribute(this.doc,
				TEMPLATE_ATTRIBUTE_EDITOR_INDEX);
		idx = Integer.valueOf(idxString);
	}

	/**
	 * 清除Undo
	 */
	@Override
	public void clearUndo() {
		undo = new ArrayList<TemplateAction>();
		undo_position = -1;
	}

	/**
	 * 添加一个Undo操作到Undo列表中
	 * 
	 * @param from
	 * @param to
	 * @param type
	 */
	@Override
	public void addUndo(Object from[], Object to[], int type) {
		while (undo.size() > undo_position + 1 && undo.size() > 0) {
			int last = undo.size() - 1;
			undo.remove(last);
		}

		TemplateAction ta = new TemplateAction();
		ta.set(from, to, type);

		undo.add(ta);
		undo_position++;

		if (undo.size() > max_undo) {
			undo.remove(0);
			undo_position--;
		}
	}

	/**
	 * 获得上一步Undo操作，并且改变Undo指针
	 * 
	 * @return
	 */
	@Override
	public TemplateAction previousUndo() {
		if (undo.isEmpty() || undo_position < 0)
			return null; // No undo left!
		TemplateAction retval = undo.get(undo_position);
		undo_position--;
		return retval;
	}

	/**
	 * 查看当前Undo操作，不改变Undo指针
	 * 
	 * @return
	 */
	@Override
	public TemplateAction viewThisUndo() {
		if (undo.isEmpty() || undo_position < 0)
			return null; // No undo left!
		TemplateAction retval = undo.get(undo_position);
		return retval;
	}

	/**
	 * 查看上一步Undo操作，不改变Undo指针
	 * 
	 * @return
	 */
	@Override
	public TemplateAction viewPreviousUndo() {
		if (undo.isEmpty() || undo_position - 1 < 0)
			return null; // No undo left!
		TemplateAction retval = undo.get(undo_position - 1);
		return retval;
	}

	/**
	 * 获得下一步Undo操作，并且改变Undo指针
	 * 
	 * @return
	 */
	@Override
	public TemplateAction nextUndo() {
		int size = undo.size();
		if (size == 0 || undo_position >= size - 1)
			return null; // no redo left...
		undo_position++;
		TemplateAction retval = undo.get(undo_position);
		return retval;
	}

	/**
	 * 查看下一步Undo 操作，不改变Undo指针
	 * 
	 * @return
	 */
	@Override
	public TemplateAction viewNextUndo() {
		int size = undo.size();
		if (size == 0 || undo_position >= size - 1)
			return null; // no redo left...
		TemplateAction retval = undo.get(undo_position + 1);
		return retval;
	}

	@Override
	public int getMaxUndo() {
		return max_undo;
	}

	@Override
	public void setMaxUndo(int mu) {
		max_undo = mu;
		while (undo.size() > mu && undo.size() > 0)
			undo.remove(0);
	}

	@Override
	public String getXML() throws KettleException {
		try {
			return XMLUtils.toXMLString(doc);
		} catch (TransformerException e) {
			throw new KettleException(e);
		}
	}

	public Document getDoc() {
		return doc;
	}

	private int getNextEditorIndex() {
		idx++;
		XMLUtils.setAttribute(doc, TEMPLATE_ATTRIBUTE_EDITOR_INDEX, String
				.valueOf(idx));
		return idx;
	}

	/**
	 * 创建一个节点对象
	 * 
	 * @param category
	 *            组件的分类
	 * @param type
	 *            组件的类型
	 * @return
	 */
	public Node createElement(String category, String type) {
		Node node = doc.createElement(type);
		XMLUtils.setAttribute(node, TEMPLATE_ATTRIBUTE_EDITOR_TYPE, type);
		XMLUtils.setAttribute(node, TEMPLATE_ATTRIBUTE_EDITOR_CATEGORY,
				category);
		XMLUtils.setAttribute(node, TEMPLATE_ATTRIBUTE_EDITOR_ID,
				TEMPLATE_ATTRIBUTE_EDITOR_ID_PREFIX + templateId + "_"
						+ getNextEditorIndex());
		return node;
	}
}
