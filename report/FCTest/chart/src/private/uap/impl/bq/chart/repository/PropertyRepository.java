package uap.impl.bq.chart.repository;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uap.vo.bq.chart.define.PropertyGroupDefine;

/**
 * 
 * @author hbyxl
 *
 */
public class PropertyRepository implements Serializable  {	

	private static final long serialVersionUID = -1L;
	private Map<String, PropertyGroupDefine> propertyGroupMap = new HashMap<String, PropertyGroupDefine>();	
	private List<PropertyGroupDefine> propertyGroupList = new ArrayList<PropertyGroupDefine>();
	
	public PropertyRepository( Map<String, PropertyGroupDefine> propertyGroupMap, List<PropertyGroupDefine> propertyGroupList ){
		this.propertyGroupMap =  propertyGroupMap;
		this.propertyGroupList = propertyGroupList;
	}
	
	/**
	 * ��ù�����������������
	 * @return
	 */
	public PropertyGroupDefine[] getPropertyGroupDefines() {
		return propertyGroupList.toArray(new PropertyGroupDefine[this.propertyGroupList.size()]);
	}

	/**
	 * ����code ���������Ϣ
	 * @param code ����code
	 * @return
	 */
	public PropertyGroupDefine getPropertyGroupDefine(String code) {
		return propertyGroupMap.get(code);	
	}

}
