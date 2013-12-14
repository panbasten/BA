package com.flywet.platform.bi.base.undo;

public interface UndoActionInterface {

	/**
	 * 设置undo事件
	 * 
	 * @param prev
	 * @param curr
	 * @param type
	 */
	public void set(Object prev[], Object curr[], int type);
}
