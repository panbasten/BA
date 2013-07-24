package com.flywet.platform.bi.report.undo;

public interface TemplateUndoInterface {
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
	public TemplateAction previousUndo();

	/**
	 * 查看当前Undo操作，不改变Undo指针
	 * 
	 * @return
	 */
	public TemplateAction viewThisUndo();

	/**
	 * 查看上一步Undo操作，不改变Undo指针
	 * 
	 * @return
	 */
	public TemplateAction viewPreviousUndo();

	/**
	 * 获得下一步Undo操作，并且改变Undo指针
	 * 
	 * @return
	 */
	public TemplateAction nextUndo();

	/**
	 * 查看下一步Undo 操作，不改变Undo指针
	 * 
	 * @return
	 */
	public TemplateAction viewNextUndo();

	public int getMaxUndo();

	public void setMaxUndo(int mu);

}
