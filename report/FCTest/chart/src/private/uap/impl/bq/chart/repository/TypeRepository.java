package uap.impl.bq.chart.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import uap.vo.bq.chart.define.EditorDefine;
import uap.vo.bq.chart.define.Type;

public class TypeRepository implements Serializable {
	
	private static final long serialVersionUID = -1L;
	private Map<String,Type> typeMap = null;
	private List<Type> typeList = null;
	
	/**
	 * 根据图表属性类型code 获得其类型信息
	 * @param code
	 * @return
	 */
	public Type getType(String code){	
		Type type = typeMap.get(code);
		return type;
	}
	
	public TypeRepository(Map<String,Type> typeMap, List<Type> typeList){
		this.typeMap = typeMap;
		this.typeList = typeList;
	}
	
	/**
	 * 获得全部类型信息数组
	 * @return
	 */
	public Type[] getTypes(){
		return typeList.toArray(new Type[typeList.size()]);
	}
	
	/**
	 * 根据图表属性类型code 获得其Editor
	 * @param typeCode 图表属性类型code
	 * @param editorName 编辑器名称
	 * @return
	 */
	public EditorDefine getEditorDefine(String typeCode, String editorName){
		Type type = getType (typeCode);
		if (null != type){
			if (editorName == null || editorName.isEmpty()){
				if (null != type.getEditorDefines() && type.getEditorDefines().length > 0)
					return type.getEditorDefines()[0];
			}
			for (EditorDefine df : type.getEditorDefines()){
				if (df.getEditorName().equals(editorName)){
					return df;
				}
			}
		}
		return null;
	}

}
