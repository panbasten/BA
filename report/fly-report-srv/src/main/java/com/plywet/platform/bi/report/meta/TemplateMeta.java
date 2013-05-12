package com.plywet.platform.bi.report.meta;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.xml.XMLInterface;
import org.pentaho.di.core.xml.XMLUtils;
import org.w3c.dom.Document;

import com.plywet.platform.bi.report.undo.TemplateAction;
import com.plywet.platform.bi.report.undo.TemplateUndoInterface;

public class TemplateMeta implements XMLInterface, Cloneable,
		TemplateUndoInterface {

	private static Class<?> PKG = TemplateMeta.class;

	protected Document doc;

	// 用于支持"undo"操作的活动列表
	protected List<TemplateAction> undo;

	// 最大undo次数
	protected int max_undo = Const.MAX_UNDO;

	// 当前undo位置
	protected int undo_position = -1;

	public TemplateMeta(Document doc) {
		this.doc = doc;
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

}
