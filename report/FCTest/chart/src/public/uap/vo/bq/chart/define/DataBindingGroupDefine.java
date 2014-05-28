package uap.vo.bq.chart.define;

/**
 * 数据绑定组定义
 * @author zhanglld
 *
 */
public class DataBindingGroupDefine extends PropertyGroupDefine{
	private static final long serialVersionUID = 1L;
	
	public String bindingGroup = null;
	
	public DataBindingGroupDefine(DataBindingGroupDefineInfo info) {
		super(info);
		this.bindingGroup = info.bindingGroup;
	}
	
	public static class DataBindingGroupDefineInfo extends PropertyGroupDefineInfo{
		public DataBindingGroupDefineInfo(PropertyGroupDefineInfo info,String bindingGroup){
			this.code = info.code;
			this.name = info.name;
			this.properties = info.properties;
			this.allowRepeat = info.allowRepeat;
			this.type = info.type;
			this.editorDefine = info.editorDefine;
			this.bindingGroup = bindingGroup;
			this.relativeChange = info.relativeChange;
		}
		public String bindingGroup = null;
	}
}
