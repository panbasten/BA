package uap.vo.bq.chart.define;

import java.io.Serializable;


public class EditorDefine implements Serializable, Cloneable{
	private static final long serialVersionUID = 200L;
	
	/*编辑类型名称*/
	private String name;
	/*重量编辑类型*/
	private String swEditorImplClass;
	/*轻量编辑类型*/
	private String lwEditorImplClass;

	public EditorDefine(String name, String swEditor, String lwEditor){
		
		this.name = name;
		swEditorImplClass = swEditor;
		lwEditorImplClass = lwEditor;
	}

	public String getSwEditorImplClass() {
		return swEditorImplClass;
	}

	public String getLwEditorImplClass() {
		return lwEditorImplClass;
	}
	
	public String getEditorName(){
		return name;
	}
	@Override
	public Object clone() {
		EditorDefine newEditorDefine = null;
		try {
			newEditorDefine = (EditorDefine)super.clone();
			newEditorDefine.name = this.name;
			newEditorDefine.lwEditorImplClass = this.lwEditorImplClass;
			newEditorDefine.swEditorImplClass = this.swEditorImplClass;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException (e);
		}
		return newEditorDefine;
	}
}
