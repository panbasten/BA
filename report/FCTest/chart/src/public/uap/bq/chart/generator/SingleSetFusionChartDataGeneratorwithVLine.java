package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.List;

import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.SumsetPropertyGroup;
import uap.vo.bq.chart.model.UserDivisionPropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class SingleSetFusionChartDataGeneratorwithVLine extends
		SingleSetFusionChartDataGenerator {
	
	
	protected List<StringBuilder> sumsets = new ArrayList<StringBuilder>();
	
	/*
	 * 生成用户定义分组线
	 * 
	 */
	@Override
	protected String generatevLine(String label){
		StringBuilder vLine = generateUserGroupLineandSumset("vValue='"+userDivisionPoint.size()+"'");
		userDivisionPoint.add(label);
		return vLine.toString();
	}
	
	
	@Override
	protected void toChartCategoryGroupLine(List<PropertyGroup> propertyGroups) {
		userGroupLines.clear();
		autoGroupLines.clear();
		sumsets.clear();

		for (PropertyGroup group : propertyGroups) {
			// 根据type, 放入缓存， 做二次生成
			if (group instanceof UserDivisionPropertyGroup) {
				this.userGroupLines.add(toGroupLine(group, "vLine"));
			} else if(group instanceof SumsetPropertyGroup){
				sumsets.add(tosumsetxml(group));
			}
		}
	}
	
	
	private StringBuilder generateUserGroupLineandSumset(String value) {
		StringBuilder vlines = new StringBuilder();
		for (StringBuilder userStringBuilder : this.userGroupLines){
			if (userStringBuilder.indexOf(value) != -1){
				vlines.append(userStringBuilder);
			}
		}
		for (StringBuilder userStringBuilder : sumsets){
			if (userStringBuilder.indexOf(value) != -1){
				vlines.append(userStringBuilder);
			}
		}
		return vlines;
	}
	
	
	/**
	 * 创建汇总
	 * 
	 */
	protected StringBuilder tosumsetxml(PropertyGroup group) {
		StringBuilder xml = new StringBuilder();
		return xml.append("<").append("set ").append(toAttributeString(group))
				.append(" isSum='1' />");
	}

	
	/**
	 * 生成<set />.....<set />片段
	 * @param dataset
	 * @return
	 */
	protected StringBuilder getSetsDataXmlPart(ChartDataset dataset){
		StringBuilder dataxml = new StringBuilder();
		ChartCategoryAxis lastCategory = categorys.get(categorys.size()-1);
		categorystack.push(lastCategory.getColumnCode());
		for (ChartBodyRow row : dataset.getChartBody().getChartBodyRows()){
			ChartDataCell categorydatacell = row.getChartDataCell(lastCategory.getColumnCode());
			String label = categorydatacell.getCaption();
			ChartDataCell seriesdatacell = row.getChartDataCell(serie.getColumnCode());
			String value = seriesdatacell.getCaption();
			
			linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(lastCategory.getColumnCode(),categorydatacell));
			linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(serie.getColumnCode(),seriesdatacell));
			
			StringBuilder link = getlink(ValueChartType.VALUE_CHART_SET);
			StringBuilder labellink = getlink(ValueChartType.VALUE_CHART_CATEGORY);
			
			linkSeriesAxisstack.pop();
			linkCategoryAixsstack.pop();
			// 计算指标和值
			if(valueSum == null){
				valueSum = getValueSum(dataset);
			}
			if (value != null && value != ""){
				value = getScaleValue(value,false);
				dataxml.append("<set label='").append(label).append("' value = '").append(value).append("' ");
				if(serie.isHasOperate()){
					dataxml.append(" link= '").append(link).append("' ");
					dataxml.append(" labellink= '").append(labellink).append("' ");
				}
				//dataxml.append(" toolText = '").append(getToolText(label, value)).append("' ");
				dataxml.append("/>");
				dataxml.append(generatevLine(label));
			}
				
		}
		categorystack.pop();
		dataxml.append(insertSumAtEnd());
		
		return dataxml;
	}
	
	
	private StringBuilder insertSumAtEnd(){
		PropertyGroup group = this.model.getPropertyGroup(PROPERTYGROUPCODE);
		boolean showSumAtEnd = getshowSumAtEnd(group);
		if(!showSumAtEnd){
			return new StringBuilder();
		}
		String sumLabel = getsumLabel(group);
		return new StringBuilder().append("<set isSum='1' label='").append(sumLabel).append("' />");
	}
	
	private boolean getshowSumAtEnd(PropertyGroup group){
		if(group == null)
			return true;
		Property showSumAtEndProperty = group.getPropertyByRefCode(SHOWSUMATENDPROPERTYCODE);
		if(showSumAtEndProperty == null)
			return true;
		return showSumAtEndProperty.getValue().equals("1");
	}
	
	private String getsumLabel(PropertyGroup group){
		if(group == null)
			return SUMLABELPROPERTYDEFAULTVALUE;
		Property sumLabelPropertyProperty = group.getPropertyByRefCode(SUMLABELPROPERTYCODE);
		if(sumLabelPropertyProperty == null)
			return SUMLABELPROPERTYDEFAULTVALUE;
		return sumLabelPropertyProperty.getValue();
	}
	
	/*
	 * 属性组Code
	 */
	static final String PROPERTYGROUPCODE = "FC_DATA_PLOT_COSMETICS";
	/*
	 * showSumAtEnd属性Code
	 */
	static final String SHOWSUMATENDPROPERTYCODE = "showSumAtEnd";
	/*
	 * sumLabel属性Code
	 */
	static final String SUMLABELPROPERTYCODE = "sumLabel";
	/*
	 * sumLabel属性缺省值
	 */
	static final String SUMLABELPROPERTYDEFAULTVALUE = "Total";
	
	
	/**
	 * 覆盖被批处理属性组里的属性值
	 * @return
	 */
	@Override
	protected StringBuilder overLapChartNodeProperty(){
		return super.overLapChartNodeProperty().append((" showSumAtEnd='0' "));
	}

}
