package com.flywet.platform.bi.base.undo;

/**
 * 实现Undo功能的接口
 * 
 * @author PeterPan
 * 
 */
public interface UndoInterface {
	/**
	 * 清除Undo
	 */
	public void clearUndo();

	/**
	 * 添加一个Undo操作到Undo列表中
	 * 
	 * @param from
	 * @param to
	 * @param type
	 */
	public void addUndo(Object from[], Object to[], int type);

	/**
	 * 获得上一步Undo操作，并且改变Undo指针
	 * 
	 * @return
	 */
	public UndoActionInterface previousUndo();

	/**
	 * 查看当前Undo操作，不改变Undo指针
	 * 
	 * @return
	 */
	public UndoActionInterface viewThisUndo();

	/**
	 * 查看上一步Undo操作，不改变Undo指针
	 * 
	 * @return
	 */
	public UndoActionInterface viewPreviousUndo();

	/**
	 * 获得下一步Undo操作，并且改变Undo指针
	 * 
	 * @return
	 */
	public UndoActionInterface nextUndo();

	/**
	 * 查看下一步Undo 操作，不改变Undo指针
	 * 
	 * @return
	 */
	public UndoActionInterface viewNextUndo();

	public int getMaxUndo();

	public void setMaxUndo(int mu);

}
