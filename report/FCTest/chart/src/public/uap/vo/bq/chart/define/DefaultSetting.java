package uap.vo.bq.chart.define;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ufida.iufo.pub.tools.AppDebug;
import com.ufida.iufo.pub.tools.DeepCopyUtilities;

import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class DefaultSetting implements Serializable, Cloneable{
	private static final long serialVersionUID = 1L;
	private String themeCode;
	
	/*默认属性值*/
	private List<PropertyGroup> propertyGroups = new ArrayList<PropertyGroup>();
	/*默认数据集值*/
	private List<ChartDataset> datasets = new ArrayList<ChartDataset>();
	/*默认绑定信息*/
	private List<IChartDataBinding> dataBindings = new ArrayList<IChartDataBinding>();

	public String getThemeCode() {
		return themeCode;
	}
	public void setThemeCode(String themeCode) {
		this.themeCode = themeCode;
	}			
	
	public PropertyGroup[] getPropertyGroups() {
		return propertyGroups.toArray(new PropertyGroup[propertyGroups.size()]);
	}
	
	public List<PropertyGroup> getPropertyGroupList() {
		return propertyGroups;
	}
	
	public void addPropertyGroups(List<PropertyGroup> groups){
		this.propertyGroups.addAll(groups);
	}
	public void addPropertyGroups(PropertyGroup group){
		this.propertyGroups.add(group);
	}
	public ChartDataset[] getDatasets() {
		return datasets.toArray(new ChartDataset[datasets.size()]);
	}
	public void addDatasets (List<ChartDataset> ds){
		this.datasets.addAll(ds);
	}
	
	public IChartDataBinding[] getDataBindings() {
		return dataBindings.toArray(new IChartDataBinding[dataBindings.size()]);
	}
	public void addChartDataBindings (List<IChartDataBinding> cdbs){
		this.dataBindings.addAll(cdbs);
	}
	public void addChartDataBindings (IChartDataBinding cdb){
		this.dataBindings.add(cdb);
	}
	@SuppressWarnings("unchecked")
	public Object clone(){
		DefaultSetting o = null;
		try {
			o = (DefaultSetting) super.clone();
			o.themeCode = this.themeCode;
			o.propertyGroups = (List<PropertyGroup>) DeepCopyUtilities.copy(this.propertyGroups);
			o.dataBindings = (List<IChartDataBinding>) DeepCopyUtilities.copy(this.dataBindings);
			o.datasets = (List<ChartDataset>) DeepCopyUtilities.copy(this.datasets);
			//处理子的克隆
		} catch (CloneNotSupportedException e) {
			AppDebug.debug(e);
		}
		
		return o;
	}

}
