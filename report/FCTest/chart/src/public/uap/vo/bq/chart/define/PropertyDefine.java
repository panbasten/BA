package uap.vo.bq.chart.define;

import java.io.Serializable;

import com.ufida.iufo.pub.tools.DeepCopyUtilities;

import uap.vo.bq.chart.define.EditorDefine;
import uap.vo.bq.chart.define.Type;


public class PropertyDefine implements Serializable, Cloneable{
	private static final long serialVersionUID = 202L;
	/*���Ա�ʾ��*/
	private String code;
	/*��������*/
	private String name;
	/*��������*/
	private Type type;
	/*����Ĭ��ֵ*/
	private String defaultValue;
	/*����ֵ��Χ*/
	private String range;
	/*��������*/
	private String description;
	/**/
	private Object isNLS;
	/*�Ƿ�������������ݵ�data.xml*/
	private boolean generate = true;
	/*���Ա༭����*/
	private EditorDefine editorDefine;
	/*����ö��ֵ*/
	private ConstraintTerm[] constraintTerms;
	/*��ظı��ʾ�����޸�����Դ�Զ���ձ�ʾ*/
	private boolean relativeChange = false;
	
	public PropertyDefine(PropertyDefineInfo info){
		this.code = info.code;
		this.name = info.name;
		this.type = info.type;
		this.defaultValue = info.defaultValue;
		this.range = info.range;
		this.description = info.description;
		this.isNLS = info.isNLS;
		this.generate = info.generate;
		this.editorDefine = info.editorDefine;
		this.constraintTerms = info.constraintTerms;
		this.relativeChange = info.relativeChange;
	}
	public PropertyDefine(PropertyDefine obj){
		this.code = obj.getCode();
		this.name = obj.getName();
		this.type = obj.getType();
		this.defaultValue = obj.getDefaultValue();
		this.range = obj.getRange();
		this.description = obj.getDescription();
		this.isNLS = obj.getAttribute1();
		this.editorDefine = obj.getEditorDefine();
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public Type getType() {
		return type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue){
		this.defaultValue = defaultValue;
	}

	public String getRange() {
		return range;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}

	public Object getAttribute1() {
		return isNLS;
	}

	public EditorDefine getEditorDefine() {
		return editorDefine;
	}
	

	public ConstraintTerm[] getConstraintTerms() {
		return constraintTerms;
	}

	public boolean isGenerate() {
		return generate;
	}
	
	public boolean isRelativeChange(){
		return this.relativeChange;
	}
	
	@Override
	public Object clone() {
		PropertyDefine newPropertyDefine = null;
		try {
			newPropertyDefine = (PropertyDefine)super.clone();
			newPropertyDefine.code = this.code;
			newPropertyDefine.name = this.name;
			newPropertyDefine.type = (Type)this.type.clone();
			newPropertyDefine.range = this.range;
			newPropertyDefine.description = this.description;
			newPropertyDefine.defaultValue = this.defaultValue;
			newPropertyDefine.isNLS = this.isNLS;
			newPropertyDefine.relativeChange = this.relativeChange;
			newPropertyDefine.generate = this.generate;
			newPropertyDefine.editorDefine = (EditorDefine) this.editorDefine.clone();
			newPropertyDefine.constraintTerms = (ConstraintTerm[]) DeepCopyUtilities.copy(this.constraintTerms);
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		return newPropertyDefine;
	}
	public static class PropertyDefineInfo{
		public String code;
		public String name;
		public Type type;
		public String defaultValue;
		public String range;
		public String description;
		public Object isNLS;
		public boolean generate = true;
		public EditorDefine editorDefine;
		public Object isHide;
		public ConstraintTerm[] constraintTerms;
		public boolean relativeChange = false;
	}
}
