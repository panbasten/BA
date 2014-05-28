package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.databinding.ChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;
import uap.vo.bq.chart.model.dataset.ChartHeaderCell;

public class MSStackedColumnChartDataGenerator extends
		MultiSeriesChartDataGenerator {
	
	
	/**
	 * 一个自定属性，多系列堆积图系列的分组属性
	 */
	public static final String KEY_GROUPCODE = "groupcode"; 
	
	/**
	 * 一个默认值，多系列堆积图系列的分组属性的缺省值
	 */
	public static final String VALUE_DEFAULTGROUP = "defaultgroup";
	
	/**
	 * 结果容器，一个键值对应一个系列分组，
	 */
	private Map<String,List<StringBuilder>> groupMap = null;
	
	
	@Override
	public void clear() {
		super.clear();
		groupMap = null;
	}
	
	
	protected void initDatasetsCan(ChartDataset dataset, DataBindingPropertyMap dbpMap){
		categoriesxml = new StringBuilder();      //储存<category label='' />。。。。。。<category label='' />信息
		dataSets_Xml  = new ArrayList<Object>();
		groupMap = new LinkedHashMap<String,List<StringBuilder>>();

		if(cate_series == null || cate_series.size()==0 )
			fill_dataSets_Xml(dataSets_Xml,dbpMap);
		else
			initDataSetXmlCan(0,dbpMap,dataSets_Xml,dataset);
		
		
	}

	protected void initDataSetXmlCan(int index, DataBindingPropertyMap dbpMap,List<Object> list,ChartDataset dataset){
		LinkedHashSet<ChartDataCell> setTemp = sets[index];
		ChartDataBinding catedatabinding = cate_series.get(index);
		String catecode = catedatabinding.getColumnCode();
		boolean cateisHasOperate = catedatabinding.isHasOperate();
		if(cateisHasOperate)
			categorystack.push(catecode);
		for(ChartDataCell cdc : setTemp){
			List<Object> listson = new ArrayList<Object>();
			list.add(listson);
			if(cateisHasOperate){
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(catecode,cdc));
			}
            valuestack.push(cdc.getCaption());
			if(index == cate_series.size()-1){
				fill_dataSets_Xml(listson,dbpMap);
				/*for(int j=0; j<real_series.size(); j++){
					StringBuilder datasetxml = new StringBuilder();
					ChartDataBinding cdb = real_series.get(j);
					boolean seriesisHasOperate = cdb.isHasOperate();
					ChartHeaderCell chc = dataset.getChartHeader().getHeaderCell(cdb.getColumnCode());
					linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cdb.getColumnCode(),null));
					String seriesName = chc.getCaption();
					datasetxml.append("<dataset seriesName='")
					          .append(valuestack.toString("_")+"_"+seriesName)
					          .append("' ");
					if(seriesisHasOperate){
					    StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_LEGEND);
					    datasetxml.append(" link='")
				                  .append(datasetlink)
				                  .append("' ");
					}
					linkSeriesAxisstack.pop();
					DataBindingRefGroup dbrg = dbpMap.get(dataset.getCode()+"-"+real_series.get(j).getColumnCode());    //根据数据集和列的code获得DataBindingRefGroup
					if(dbrg!=null)datasetxml.append(toAttributeString(dbrg));   //给<dataset ....>加属性
					datasetxml.append(">");
					listson.add(datasetxml);
					
				}*/

			}else
				initDataSetXmlCan(index+1,dbpMap,listson,dataset);
			valuestack.pop();
			if(cateisHasOperate){
				linkCategoryAixsstack.pop();
			}
				
		}
		if(cateisHasOperate)
			categorystack.pop();
	}
	
	
	
	private void fill_dataSets_Xml(List<Object> list , DataBindingPropertyMap dbpMap){
		for(int i=0; i<real_series.size(); i++){
			StringBuilder datasetxml = new StringBuilder();
			ChartDataBinding cdb = real_series.get(i);
			boolean databindingisHasOperate = cdb.isHasOperate();
			ChartHeaderCell chc = dataset.getChartHeader().getHeaderCell(cdb.getColumnCode());
			linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cdb.getColumnCode(),null));
			String seriesName = chc.getCaption();
			datasetxml.append("<dataset seriesName='")
			          .append((valuestack!=null && !valuestack.isEmpty())?valuestack.toString("_")+"_"+seriesName:seriesName)
			          .append("' ");
			if(databindingisHasOperate){
				StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_LEGEND);;
				datasetxml.append(" link='").append(datasetlink).append("' ");
			}
			linkSeriesAxisstack.pop();
			DataBindingRefGroup dbrg = dbpMap.get(dataset.getCode()+"-"+real_series.get(i).getColumnCode());    //根据数据集和列的code获得DataBindingRefGroup
			if(dbrg!=null)datasetxml.append(toAttributeString(dbrg));   //给<dataset ....>加属性
			datasetxml.append(">");
			list.add(datasetxml);
			
			String groupkey = getGroupcoedFromDataBindingRefGroup(dbrg);
			if(groupkey == null || groupkey.equals(""))
				groupkey = MSStackedColumnChartDataGenerator.VALUE_DEFAULTGROUP;
			if(!groupMap.containsKey(groupkey))
				groupMap.put(groupkey, new ArrayList<StringBuilder>());
			groupMap.get(groupkey).add(datasetxml);
		}
	}
	
	protected void fillDataset(StringBuilder dataxml,List<Object> list){
		for(String groupkey : groupMap.keySet()){
			dataxml.append("<dataset>");
			List<StringBuilder> stringbuilderlist = groupMap.get(groupkey);
			for(StringBuilder sbcell : stringbuilderlist)
				dataxml.append(sbcell).append("</dataset>");
			dataxml.append("</dataset>");
			
		}
	}
	
	
	private String getGroupcoedFromDataBindingRefGroup(DataBindingRefGroup binding){
		if(binding == null)
			return MSStackedColumnChartDataGenerator.VALUE_DEFAULTGROUP;
		else {
			for (Property property : binding.getProperties()) {
				if (property.getCode().equals(MSStackedColumnChartDataGenerator.KEY_GROUPCODE) ){
					String temp =  property.getValue();
					if(temp!=null && !temp.equals("")){
						 //property.setValue(null);
						 return temp;
					}
				}
			}
		}
		return MSStackedColumnChartDataGenerator.VALUE_DEFAULTGROUP;
			
	}
	
	/*
	 * 覆盖此方法，忽略与趋势线的值得比较
	 * @see uap.bq.chart.generator.MultiSeriesChartDataGenerator#compareValueWithTrendlines(java.lang.String)
	 */
	protected String compareValueWithTrendlines(String seriesvaluecode){
		return "";
	}
	
	
}
