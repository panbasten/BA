package uap.bq.chart.generator;

import java.util.List;

import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class SparkChartDataGenerator extends BaseChartDataGenerator {
	
	/**
	 * 创建统计图fusionchart渲染数据
	 * 
	 * @param dataBindList
	 * @param dataBindingPropMap
	 * @param datasetMap
	 * @return StringBuilder，渲染数据字符串构造器对象
	 * @throws ChartGenerateException
	 */
	public StringBuilder toChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {
		StringBuilder xml = new StringBuilder();
		String seriescode = null;
		for(DataBindingItem dbi : dataBindingItems){
			if(dbi.getChartDataBinding() instanceof ChartSeriesAxis){
				seriescode = dbi.getChartDataBinding().getColumnCode();
				break;
			}
		}
		if(seriescode == null || seriescode.equals(""))return xml;
		xml.append("<dataset>");
		for( ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()){
			String value = cbr.getChartDataCell(seriescode).getCaption();
			xml.append("<set value='").append(value).append("' />");
		}
		xml.append("</dataset>");
		return xml;
	}

}
