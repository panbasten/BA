package uap.vo.bq.chart.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性组
 * @author zhanglld
 *
 */
public class PropertyGroup extends AbstractModelElement<IModelObject>{
	private static final long serialVersionUID = 1L;
	
	/*引用到chartdefine中的code*/
	private String refCode;
	/*修改增加的数组属性值*/
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
//				// setCode(refCode); // by wangqzh modified. 不能在getCode中调用setCode，会造成changed标记改变。
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
//							//说明这个编码不是默认规则生成的，跳过即可
//							continue;
//						}
//					}
//				}
//				
//				for(int i = 0; i < nullCodeGroupList.size(); i ++){
//					//nullCodeGroupList.get(i).setCode(refCode + "." + (max + 1 + i)); // setCode 会造成 changed改变
//					code = refCode + "." + (max + 1 + i);;
//				}
//			}
		}
		
		return super.getCode();
	}
	
	/**
	 * 获得原始的编码
	 * @return
	 */
	private String getRawCode(){
		return super.getCode();
	}
	
	/**
	 * 获得所属ChartModel
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
	 * 根据定义编码获得属性
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
	 * 获得全部属性
	 * @return
	 */
	public List<Property> getProperties() {
		return properties;
	}
	
	/**
	 * 获得引用的定义码
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