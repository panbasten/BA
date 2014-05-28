package uap.bq.chart.generator;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.bq.chart.ChartPropConst;
import uap.bq.chart.generator.link.JsonStack;
import uap.bq.chart.utils.ChartNumFormatUtils;
import uap.util.bq.chart.ChartUtil;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;


/**
 * 
 * @author wangqzh
 *
 */
/* 支持 set@value 的统计图 */
public class SingleSetFusionChartDataGenerator extends LinkChartDataGenerator{
	
	
	/*
	 * 瀑布图code
	 */
	private final static String WATERFALL2DCODE = "Waterfall2D";
	
	
	/*
	 * 系列
	 */
	protected ChartSeriesAxis     serie = null;
	/*
	 * 分类
	 */
	protected List<ChartCategoryAxis> categorys = null;
	
	protected Double valueSum = null;
	
	
	/**
	 * 覆盖被批处理属性组里的属性值
	 * @return
	 */
	protected StringBuilder overLapChartNodeProperty(){
		if(this.model.getChartDefineCode()==null || !this.model.getChartDefineCode().equals(WATERFALL2DCODE))
			return new StringBuilder();
		return super.overLapChartNodeProperty();
	}
	
	
	
	
	@Override
	/**
	 *  生成 <set label = "" value = ""/>
	 */
	public StringBuilder generateLinkChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException{
		
		if (dataBindingItems == null || dataBindingItems.size()==0)
			return new StringBuilder();
		
		classifySeriesAndCategory(dataBindingItems);
		
		if( categorys.size()==0 || serie==null )
			return new StringBuilder();
		
		pushlinkCategoryAixsstackAndcategorystack(dataset.getChartBody().getChartBodyRows()[0]);
		
		StringBuilder dataxml = getSetsDataXmlPart(dataset);
		
		poplinkCategoryAixsstackAndcategorystack();
		
		return dataxml;
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
			String value = StringUtils.isEmpty(seriesdatacell.getCaption()) ? "0":seriesdatacell.getCaption();
			//value = getScaleValue(value,false);
			
			linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(lastCategory.getColumnCode(),categorydatacell));
			linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(serie.getColumnCode(),seriesdatacell));
			
			StringBuilder link = getlink(ValueChartType.VALUE_CHART_SET);
			StringBuilder lgndlink = getlink(ValueChartType.VALUE_CHART_LEGEND);
			
			linkSeriesAxisstack.pop();
			linkCategoryAixsstack.pop();
			// 计算指标和值
			if(valueSum == null){
				valueSum = getValueSum(dataset);
			}
			if (value != null && value != ""){
				//dataxml.append("<set label='").append(label).append("' value = '").append(value).append("' ");
				dataxml.append("<set label='").append(label).append("' value = '").append(getScaleValue(value, false)).append("' ");
				//.append("' displayValue = '").append(getScaleDisplayValue(value, false)).append("' ")
				if(serie.isHasOperate()){
					dataxml.append(" link= '").append(link).append("' ");
					dataxml.append(" lgndlink= '").append(lgndlink).append("' ");
				}
				dataxml.append(" toolText = '").append(getToolText(label, value)).append("' ");
				dataxml.append("/>");
				dataxml.append(generatevLine(label));
			}
				
		}
		categorystack.pop();
		
		return dataxml;
	}
	
	/**
	 * 把信息压入栈
	 * @param row
	 */
	private void pushlinkCategoryAixsstackAndcategorystack(ChartBodyRow row){
		ChartCategoryAxis category;
		ChartDataCell categorydatacell;
		for(int i=0; i<categorys.size()-1; i++){
			category = categorys.get(i);
			categorystack.push(category.getColumnCode());
			categorydatacell = row.getChartDataCell(category.getColumnCode());
			linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(category.getColumnCode(),categorydatacell));
		}
	}
	/**
	 * 把信息压出栈
	 */
	private void poplinkCategoryAixsstackAndcategorystack(){
		for(int i=0; i<categorys.size()-1; i++){
			categorystack.pop();
			linkCategoryAixsstack.pop();
		}
	}
	
	
	/**
	 * 分解出系列和分类
	 * @param dataBindingItems
	 */
	private void classifySeriesAndCategory(List<DataBindingItem> dataBindingItems){
		categorys = new LinkedList<ChartCategoryAxis>();
		for (DataBindingItem dbg : dataBindingItems){
			if (ChartUtil.isCategoryAxisObject(dbg.getChartDataBinding())){
				categorys.add( (ChartCategoryAxis)dbg.getChartDataBinding() );
			}
			else if (ChartUtil.isSeriesAxisObject(dbg.getChartDataBinding())){
				serie = (ChartSeriesAxis)dbg.getChartDataBinding();
			}
		}
	}
	
	
	
	/**
	 * 生成用户定义分组线
	 * 
	 */
	protected String generatevLine(String label){
		return "";
	}
	
	protected String getToolText(String category,String value){
		
		JsonStack<String> toolText = new JsonStack<String>();	
		PropertyGroup propertyGroup = model.getPropertyGroup(ChartPropConst.PROPGROUP_FC_TOOL_TIP);
		if(propertyGroup == null ){
			return toolText.toString();
		}
		for(Property property : propertyGroup.getProperties()){
			if(ChartPropConst.TOOLTIP_CATEGORY.equals(property.getRefCode())){
				if("1".equals(property.getValue())){
					toolText.push(category);
				}
			}else if(ChartPropConst.TOOLTIP_PERCENT.equals(property.getRefCode())){
				if("1".equals(property.getValue())){
					toolText.push(ChartNumFormatUtils.getFormatPercent(valueSum, value, false, model));
				}else{
					toolText.push(getScaleDisplayValue(value, false));
				}
			}
		}	
		
		return toolText.toString(getToolTipSepChar());
		
	}
	
	/**
	 * 获取指标和值
	 * @param dataset
	 * @return
	 */
	protected Double getValueSum(ChartDataset dataset){
		Double sum = new Double("0");
		for (ChartBodyRow row : dataset.getChartBody().getChartBodyRows()){			
			ChartDataCell seriesdatacell = row.getChartDataCell(serie.getColumnCode());
			String value = seriesdatacell.getCaption();
			if(!ChartNumFormatUtils.isDigital(value)){
				return null;
			}			
			sum += Double.valueOf(value);
		}
		return sum;		
	}
	
	/**
	 * 获取悬浮提示分割符号
	 */
	private String getToolTipSepChar(){
		String toolTipSepChar = null;
		PropertyGroup propertyGroup = model.getPropertyGroup(ChartPropConst.PROPGROUP_FC_TOOL_TIP);
		toolTipSepChar = ChartNumFormatUtils.getPropertyValue(propertyGroup, ChartPropConst.TOOLTIP_SEPCHAR);
		return toolTipSepChar == null ? "," : toolTipSepChar;
		
	}
	
	
}
