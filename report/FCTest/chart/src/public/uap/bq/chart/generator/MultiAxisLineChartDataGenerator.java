package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.databinding.MultiLevelChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class MultiAxisLineChartDataGenerator extends BaseChartDataGenerator {
	
	@Override
	public StringBuilder toChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {
		
		StringBuilder xml = new StringBuilder();
		xml.append(generatecategories(dataBindingItems,dbpMap,dataset));
		xml.append(generateaxises(dataBindingItems,dbpMap,dataset));
		
		return xml;
	}
	
	private StringBuilder generatecategories(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset){
		
		String colcode = null;
		for(DataBindingItem dbi : dataBindingItems)
			if(dbi.getChartDataBinding() instanceof ChartCategoryAxis){
				colcode = dbi.getChartDataBinding().getColumnCode();
				break;
			}
		StringBuilder categoriesxml = new StringBuilder();
		categoriesxml.append("<categories>");
		String labletemp = null;
		for( ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()){
			labletemp = cbr.getChartDataCell(colcode).getCaption();
			categoriesxml.append("<category label='").append(labletemp).append("' />");
		}
		categoriesxml.append("</categories>");
		
		return categoriesxml;
	}
	
	
	private StringBuilder generateaxises(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset){
		
		HashMap<String,List<ChartSeriesAxis>> seriesmap = new HashMap<String,List<ChartSeriesAxis>>();
		
		for(DataBindingItem dbi : dataBindingItems)
			if(dbi.getChartDataBinding() instanceof MultiLevelChartSeriesAxis){
				MultiLevelChartSeriesAxis mlsa = (MultiLevelChartSeriesAxis) dbi.getChartDataBinding();
				String axiscode = mlsa.getAxiscode();
				if(seriesmap.containsKey(axiscode)) seriesmap.get(axiscode).add(mlsa);
				else{
					seriesmap.put(axiscode,new ArrayList<ChartSeriesAxis>());
					seriesmap.get(axiscode).add(mlsa);
				}
			}
		
		StringBuilder axisesxml = new StringBuilder();
		for (String key : seriesmap.keySet()) axisesxml.append( generateaxis(key,seriesmap.get(key),dbpMap,dataset) );
		
		return axisesxml;
	}
	
	
	private StringBuilder generateaxis(String key,List<ChartSeriesAxis> list,
			DataBindingPropertyMap dbpMap, ChartDataset dataset){
		
		StringBuilder axisxml = new StringBuilder();
		axisxml.append("<axis title='").append(key).append("' >");
		for(ChartSeriesAxis csa : list){
			String axiscode = csa.getColumnCode();
			String axisname = dataset.getChartHeader().getHeaderCell(axiscode).getCaption();
			axisxml.append("<dataset seriesName='").append(axisname).append("' >");
			String valuetemp = null;
			for( ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()){
				valuetemp = cbr.getChartDataCell(axiscode).getCaption();
				axisxml.append("<set value='").append(valuetemp).append("' />");
			}
			axisxml.append("</dataset>");
		}
		axisxml.append("</axis>");
		return axisxml;
	}
	
	
	protected StringBuilder toChartTrendlines(List<PropertyGroup> propertyGroups) {
		return new StringBuilder();
	}

	
}
