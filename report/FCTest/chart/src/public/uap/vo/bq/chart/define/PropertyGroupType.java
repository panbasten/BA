package uap.vo.bq.chart.define;

import java.io.Serializable;

/**
 * 属性组类型
 * 
 * @author zhanglld
 *
 */
public class PropertyGroupType implements Serializable {	
	private static final long serialVersionUID = -237928765239856350L;
	/**/
	private EditorDefine[] editorDefines;
	/*标示符*/
	private String code;
	/*名称*/
	private String name;
	/*枚举值*/
	private String enumValue;
	/*数值类型*/
	private String valueType;
	
	public PropertyGroupType(PropertyGroupTypeInfo info){
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
	
	public static class PropertyGroupTypeInfo{
		public EditorDefine[] editorDefines;
		public String code;
		public String name;
		public String enumValue;
		public String valueType;
	}
}
