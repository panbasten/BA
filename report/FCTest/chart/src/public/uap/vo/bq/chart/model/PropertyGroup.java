package uap.vo.bq.chart.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ������
 * @author zhanglld
 *
 */
public class PropertyGroup extends AbstractModelElement<IModelObject>{
	private static final long serialVersionUID = 1L;
	
	/*���õ�chartdefine�е�code*/
	private String refCode;
	/*�޸����ӵ���������ֵ*/
	private List<Property> properties = new RecordChangeList<Property>();
	
	public PropertyGroup(String refCode){
		this.refCode = refCode;
		((RecordChangeList<Property>)properties).setSuperObject(this);
	}
	@Override
	public String getCode() {
		if(super.getCode() == null){
			return this.getRefCode();
//			ChartModel model = getChartModel();
//			
//			assert model != null;
//			
//			PropertyGroup[] groups = model.getPropertyGroups(refCode);
//			if(groups.length == 1){
//				// setCode(refCode); // by wangqzh modified. ������getCode�е���setCode�������changed��Ǹı䡣
//				code = refCode;
//			}else{
//				List<PropertyGroup> nullCodeGroupList = new ArrayList<PropertyGroup>();
//				int max = -1;
//				for(PropertyGroup group : groups){
//					int from = refCode.length();
//					String rawCode = group.getRawCode();
//					if(rawCode == null){
//						nullCodeGroupList.add(group);
//						continue;
//					}
//					
//					int to = rawCode.length();
//					if(from != to){
//						try{
//							int value = Integer.valueOf(rawCode.substring(from + 1, to));
//							if(value > max){
//								max = value;
//							}
//						}catch(Exception e){
//							//˵��������벻��Ĭ�Ϲ������ɵģ���������
//							continue;
//						}
//					}
//				}
//				
//				for(int i = 0; i < nullCodeGroupList.size(); i ++){
//					//nullCodeGroupList.get(i).setCode(refCode + "." + (max + 1 + i)); // setCode ����� changed�ı�
//					code = refCode + "." + (max + 1 + i);;
//				}
//			}
		}
		
		return super.getCode();
	}
	
	/**
	 * ���ԭʼ�ı���
	 * @return
	 */
	private String getRawCode(){
		return super.getCode();
	}
	
	/**
	 * �������ChartModel
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private ChartModel getChartModel(){
		IModelObject superObj = this.getSuperObject();
		do{
			if(superObj == null ){
				return null;
			} else if(superObj instanceof ChartModel){
				return (ChartModel)superObj;
			}else{
				IModelElement superEle = (IModelElement) superObj;
				superObj = superEle.getSuperObject();
			}
		}while(true);
	}
	
	/**
	 * ���ݶ������������
	 * @param code
	 * @return
	 */
	public Property getPropertyByRefCode(String code){
		for ( Property prop : this.properties ){
			if ( prop.getRefCode().equals(code)){
				return prop;
			}
		}
		return null;
	}
	
	@Override
	public void setChange(boolean isChanged) {
		super.setChange(isChanged);
		if(!isChanged){
			for(Property property : properties){
				if(property.isChanged()){
					property.setChange(false);
				}
			}
		}
	}

	/**
	 * ���ȫ������
	 * @return
	 */
	public List<Property> getProperties() {
		return properties;
	}
	
	/**
	 * ������õĶ�����
	 * @return
	 */
	public String getRefCode() {
		return refCode;
	}
	
	@Override
	public Object clone(){
		PropertyGroup newGroup = (PropertyGroup) super.clone();
		@SuppressWarnings("unchecked")
		RecordChangeList<Property> newProperties = 
			(RecordChangeList<Property>) ((RecordChangeList<Property>)properties).clone();
		newProperties.setSuperObject(newGroup);
		newGroup.properties = newProperties;
		return newGroup;
	}
}