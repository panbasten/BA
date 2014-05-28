package uap.vo.bq.chart.define;

import java.io.Serializable;

import com.ufida.iufo.pub.tools.DeepCopyUtilities;


public class Type implements Serializable, Cloneable{
	private static final long serialVersionUID = 203L;
	/*editor类型数组*/
	private EditorDefine[] editorDefines;
	/*类型的标示符*/
	private String code;
	/*类型名称*/
	private String name;
	/*类型的枚举值*/
	private String enumValue;
	/*类型的数值*/
	private String valueType;
		
	public Type(PropertyTypeInfo info){
		this.code = info.code;
		this.name = info.name;
		this.editorDefines = info.editorDefines;
		this.enumValue = info.enumValue;
		this.valueType = info.valueType;
	}
	
	public EditorDefine[] getEditorDefines() {
		return editorDefines;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getEnumValue() {
		return enumValue;
	}

	public String getValueType() {
		return valueType;
	}
	@Override
	public Object clone() {
		Type newType = null;
		try {
			newType = (Type)super.clone();
			newType.code = this.code;
			newType.name = this.name;
			newType.valueType = this.valueType;
			newType.enumValue = this.enumValue;
			newType.editorDefines = (EditorDefine[]) DeepCopyUtilities.copy(this.editorDefines);
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException (e);
		}
		return newType;
	}
	public static class PropertyTypeInfo{
		public EditorDefine[] editorDefines;
		public String code;
		public String name;
		public String enumValue;
		public String valueType;
	}
	
	

}
