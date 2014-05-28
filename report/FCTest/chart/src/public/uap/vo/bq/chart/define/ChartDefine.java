package uap.vo.bq.chart.define;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ufida.iufo.pub.tools.DeepCopyUtilities;


public class ChartDefine implements Serializable, Cloneable{
	private static final long serialVersionUID = 200L;
	/* ͼ���� */
	private String width;
	/* ͼ��߶� */
	private String height;
	/*ͼ��������ʾ*/
	private int index;
	/*chart��ʾ�� �磺MSColumn2D */
	private String code;
	/*ͼ������*/
	private String name;
	/*ͼ�����������*/
	private String category;
	/*ͼ������*/
	private String type;
	/*ͼ��ͼ��*/
	private Icon icon;
	/*ͼ������ֵ��*/
	private PropertyGroupDefine[] propertyGroupDefines;
	/*ͼ���Ĭ��������Ϣ*/
	private DefaultSetting defaultSetting;
	/*ͼ��Ŀ�����������Ϣ*/
	private DefaultSetting noDataDefaultSetting;
	/*ͼ���API�ӿڶ���*/
	private APIDefine[] apiDefines;
	/*ͼ����¼��ӿڶ���*/
	private EventDefine[] eventDefines;
	private String xmlFile;
	private Map<String,Object> mapCustomProperty = new HashMap<String,Object>(); 
	
	
	public ChartDefine(ChartDefineInfo info){
		this.width = info.width;
		this.height= info.height;
		this.index = info.index;
		this.code = info.code;
		this.name = info.name;
		this.category = info.category;
		this.type = info.type;
		this.icon = info.icon;
		this.propertyGroupDefines = info.propertyGroupDefines;
		this.defaultSetting = info.defaultSetting;
		this.noDataDefaultSetting = info.noDataDefaultSetting;
		this.apiDefines = info.apiDefines;
		this.eventDefines = info.eventDefines;
		if (info.mapCustomProperty != null && info.mapCustomProperty.size() != 0){
			this.mapCustomProperty.putAll(info.mapCustomProperty);
		}
	}
	
	public String getWidth(){
		return width;
	}
	
	public String getHeight(){
		return height;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getCode() {
		return code;
	}

	
	public Icon getIcon() {
		return icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public PropertyGroupDefine[] getPropertyGroupDefines() {
		return propertyGroupDefines;
	}


	public DefaultSetting getDefaultSetting() {
		return defaultSetting;
	}
	
	public DefaultSetting getNoDataDefaultSetting() {
		return noDataDefaultSetting;
	}
	

	public APIDefine[] getApiDefines() {
		return apiDefines;
	}

	public EventDefine[] getEventDefines() {
		return eventDefines;
	}
	
	public String getChartDefineFilePath(){
		return xmlFile;
	}
	
	public void setChartDefineFilePath (String filePath){
		xmlFile = filePath;
	}
	
	
	public boolean isCustomProperty (String name){
		return mapCustomProperty.containsKey(name);
	}
	
	/**
	 * �ж��������Ƿ�����������ֵ�����ı�
	 * @param code
	 * @return
	 */
	public boolean isRelativePropertyGroup(String code){
		PropertyGroupDefine def = this.getPropertyGroupDefine(code);
		if ( def == null )
			return false;
		return def.isRelativeChange();
	}
	
	/**
	 * �ж������Ƿ�����������ֵ�����ı�
	 * @param groupCode
	 * @param code
	 * @return
	 */
	public boolean isRelativeProperty(String groupCode, String code){
		PropertyGroupDefine def = this.getPropertyGroupDefine(groupCode);
		if ( def == null )
			return false;
		if ( def.getProperties() == null)
			return false;
		
		for ( PropertyDefine propDef : def.getProperties() ){
			if ( propDef == null )
				continue;
			if ( propDef.getCode().equals(code)){
				return propDef.isRelativeChange();
			}		
		}
		
		return false;
	}
	
	// ��ȡ������
	public PropertyGroupDefine getPropertyGroupDefine (String code){
		if (code == null || code.isEmpty()){
			return null;
		}
		
		for (PropertyGroupDefine item : propertyGroupDefines){
			if (item.getCode().equals(code)){
				return item;
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		ChartDefine newChartDefine = null;
		try {
			newChartDefine = (ChartDefine)super.clone();
			newChartDefine.width = this.width;
			newChartDefine.height = this.height;
			newChartDefine.index = this.index;
			newChartDefine.code = this.code;
			newChartDefine.name = this.name;
			newChartDefine.category = this.category;
			newChartDefine.type = this.type;
			newChartDefine.xmlFile = this.xmlFile;
			newChartDefine.apiDefines = (APIDefine[]) DeepCopyUtilities.copy(this.apiDefines);
			newChartDefine.defaultSetting = (DefaultSetting) this.defaultSetting.clone();
			newChartDefine.eventDefines = (EventDefine[]) DeepCopyUtilities.copy(this.eventDefines);
			newChartDefine.icon = (Icon) this.icon.clone();
			newChartDefine.propertyGroupDefines = (PropertyGroupDefine[]) DeepCopyUtilities.copy(this.propertyGroupDefines);
			newChartDefine.mapCustomProperty = (Map<String, Object>) DeepCopyUtilities.copy(this.mapCustomProperty);
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException (e);
		}
		return newChartDefine;
	}

	public static class ChartDefineInfo{		
		public String width;
		public String height;
		public int index;
		public String code;
		public String name;
		public String category;
		public String type;
		public Icon icon;
		public PropertyGroupDefine[] propertyGroupDefines;
		public DefaultSetting defaultSetting;
		/*ͼ��Ŀ�����������Ϣ*/
		public DefaultSetting noDataDefaultSetting;
		public APIDefine[] apiDefines;
		public EventDefine[] eventDefines;
		public Map<String,Object> mapCustomProperty;
	}
}
