package uap.vo.bq.chart.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * �����鶨��
 * 
 * @author zhanglld
 *
 */
public class PropertyGroupDefine implements Serializable, Cloneable{
	private static final long serialVersionUID = -1L;
	
	/*�������а���������ֵ*/
	private PropertyDefine[] properties;
	/*�������ʾ��*/
	private String code;
	/*����������*/
	private String name;
	/*�������Ƿ�����ظ�*/
	private boolean allowRepeat = false;
	/*����������*/
	private Type type = null;
	/*������༭����*/
	private EditorDefine editorDefine;
	/*��ظı��ʾ�����޸�����Դ�Զ���ձ�ʾ*/
	private boolean relativeChange = false;
	
	public PropertyGroupDefine(PropertyGroupDefineInfo info){
		this.code = info.code;
		this.name = info.name;
		this.properties = info.properties;
		this.allowRepeat = info.allowRepeat;
		this.type = info.type;
		this.editorDefine = info.editorDefine;
		this.relativeChange = info.relativeChange;
	}
	
	public PropertyDefine[] getProperties() {
		return properties;
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

	public boolean isRepeatable() {
		return allowRepeat;
	}
	
	public boolean isRelativeChange(){
		return relativeChange;
	}
	
	public Type getType() {
		return type;
	}

	public EditorDefine getEditorDefine() {
		return editorDefine;
	}
	@Override
	public Object clone() {
		PropertyGroupDefine newGroupDefine;
		try {
			newGroupDefine = (PropertyGroupDefine) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		
		List<PropertyDefine> newProperties = new ArrayList<PropertyDefine>();
		if(this.properties != null){
			for(PropertyDefine define : this.properties){
				newProperties.add((PropertyDefine) define.clone());
			}

			newGroupDefine.properties = newProperties.toArray(new PropertyDefine[this.properties.length]);
		}
		
		return newGroupDefine;
	}

	public static class PropertyGroupDefineInfo{
		public PropertyDefine[] properties;
		public String code;
		public String name;
		public boolean allowRepeat = false;
		public Type type = null;
		public EditorDefine editorDefine;
		public boolean relativeChange = false;
	}
	
}