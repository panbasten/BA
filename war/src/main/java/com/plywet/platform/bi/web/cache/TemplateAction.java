package com.plywet.platform.bi.web.cache;

import org.pentaho.di.i18n.BaseMessages;

public class TemplateAction {
	private static Class<?> PKG = TemplateAction.class;

	public static final int TYPE_ACTION_NONE 			= 0;
	public static final int TYPE_ACTION_MOVE_NODE 		= 1;
	public static final int TYPE_ACTION_NEW_NODE 		= 2;
	public static final int TYPE_ACTION_CHANGE_NODE 	= 3;
	public static final int TYPE_ACTION_DELETE_NODE 	= 4;

	public static final String TYPE_ACTION_DESCRIPTION[] = new String[] { 
		"",
		BaseMessages.getString(PKG, "TemplateAction.label.MoveNode"),
		BaseMessages.getString(PKG, "TemplateAction.label.NewNode"),
		BaseMessages.getString(PKG, "TemplateAction.label.ChangeNode"),
		BaseMessages.getString(PKG, "TemplateAction.label.DeleteNode"),
	};
	
	private int type;
	private Object previous[];
	
	private Object current[];
	
	public TemplateAction() {
		type = TYPE_ACTION_NONE;
	}

	/**
	 * 删除一个节点
	 * 
	 * @param prev
	 */
	public void setDelete(Object prev[]) {
		current = prev;
		type = TYPE_ACTION_DELETE_NODE;
	}

	public void setChanged(Object prev[], Object curr[]) {
		previous = prev;
		current = curr;
		type = TYPE_ACTION_CHANGE_NODE;
	}
	
	public void setNew(Object prev[]) {
		if (prev == null || prev.length == 0)
			return;
		current = prev;
		type = TYPE_ACTION_NEW_NODE;
	}

	public void setMoved(Object prev[], Object curr[]) {
		previous = prev;
		current = curr;
		type = TYPE_ACTION_MOVE_NODE;
	}

	public int getType() {
		return type;
	}

	public Object[] getPrevious() {
		return previous;
	}

	public Object[] getCurrent() {
		return current;
	}
	
}
