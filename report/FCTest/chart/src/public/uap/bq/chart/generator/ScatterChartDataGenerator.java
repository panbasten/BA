package uap.bq.chart.generator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartXYSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class ScatterChartDataGenerator extends BaseChartDataGenerator {
	
	@Override
	public StringBuilder toChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {
		StringBuilder xml = new StringBuilder();
		
		ChartXYSeriesAxis cxysa = null;
		ChartCategoryAxis cca = null;
		for(DataBindingItem dbi : dataBindingItems){
			if(dbi.getChartDataBinding() instanceof ChartXYSeriesAxis)
				cxysa =(ChartXYSeriesAxis)dbi.getChartDataBinding();
			else if	(dbi.getChartDataBinding() instanceof ChartCategoryAxis)
				cca = (ChartCategoryAxis)dbi.getChartDataBinding();
		}
		String xcolumnCode = cxysa.getxColumnCode();
		
		
			xml.append(generatecategories(xcolumnCode,dataset));
		    xml.append(generatedatasets(cxysa,cca,dbpMap,dataset));
		
		
		return xml;
	}
	
	
	
	private StringBuilder generatecategories(String xcolumnCode , ChartDataset dataset) {
		StringBuilder categories  = new StringBuilder();
		categories.append("<categories>");
		
		double minvalue = Double.POSITIVE_INFINITY ;
		double maxvalue = Double.NEGATIVE_INFINITY ;
		
		String xvaluetemp;
		double temp;
		
		for( ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()){
			    xvaluetemp = cbr.getChartDataCell(xcolumnCode).getCaption();
				temp = Double.parseDouble(xvaluetemp);
				minvalue = minvalue>temp ? temp : minvalue;
				maxvalue = maxvalue>temp ? maxvalue : temp;
		}
		
		int n=10;
		double dtemp = ( maxvalue-minvalue ) / n;
		DecimalFormat df=new DecimalFormat("0");
		String categorytemp;
		
		for(int i=0; i<=n; i++){
			categorytemp = df.format(minvalue+dtemp*i);
			categories.append("<category label='").append(categorytemp).append("' x='")
	          .append(categorytemp).append("' showVerticalLine='1' />");
		}
		
		categories.append("</categories>");
		
		return categories;
	}
	
	
	protected StringBuilder generatedatasets(ChartXYSeriesAxis cxysa,ChartCategoryAxis cca,DataBindingPropertyMap dbpMap, ChartDataset dataset) {
		
		StringBuilder datasetsxml = new StringBuilder();
		
		if(cca == null || cca.getColumnCode().isEmpty()){
			datasetsxml.append("<dataset ");
			DataBindingRefGroup dbrg = dbpMap.get(dataset.getCode()+"-"+cxysa.getCode());    //根据数据集和列的code获得DataBindingRefGroup
			if(dbrg!=null)datasetsxml.append(toDataBindingRefGroupAttributeString(dbrg));   //给<dataset ....>加属性
			datasetsxml.append(" >");
			datasetsxml.append(generatedatasetxml(cxysa,Arrays.asList(dataset.getChartBody().getChartBodyRows() ) ) );
			datasetsxml.append("</dataset>");
			return datasetsxml;
		}
		
		String colcode = cca.getColumnCode();
		HashMap<String,List<ChartBodyRow>> valueMaplist = new HashMap<String,List<ChartBodyRow>>();
		for (ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()){
			String categoryvalue = cbr.getChartDataCell(colcode).getCaption();
			if(valueMaplist.containsKey(categoryvalue))valueMaplist.get(categoryvalue).add(cbr);
			else{
				valueMaplist.put(categoryvalue, new ArrayList<ChartBodyRow>());
				valueMaplist.get(categoryvalue).add(cbr);
			}
		}
		
		
		for(String key : valueMaplist.keySet()){
			datasetsxml.append("<dataset seriesName='").append(key).append("' ");
			//DataBindingRefGroup dbrg = dbpMap.get(dataset.getCode()+"-"+cxysa.getCode());    //根据数据集和列的code获得DataBindingRefGroup
			//if(dbrg!=null)datasetsxml.append(toDataBindingRefGroupAttributeString(dbrg));   //给<dataset ....>加属性
			datasetsxml.append(" >");
			
			datasetsxml.append(generatedatasetxml(cxysa , valueMaplist.get(key)));
			
			datasetsxml.append("</dataset>");
		}
		
		return datasetsxml;
	}
	
	protected StringBuilder generatedatasetxml(ChartXYSeriesAxis cxysa,List<ChartBodyRow> chartBodyRows){
		String xcolumnCode = cxysa.getxColumnCode();
		String ycolumnCode = cxysa.getyColumnCode();
		StringBuilder datasetxml = new StringBuilder();
		for (ChartBodyRow cbr : chartBodyRows){
			String xvalue = cbr.getChartDataCell(xcolumnCode).getCaption();
		    String yvalue = cbr.getChartDataCell(ycolumnCode).getCaption();
		    yvalue = getScaleValue(yvalue,false);
		    if(xvalue == null || xvalue.equals("") || yvalue == null || yvalue.equals("")) continue;
		    datasetxml.append("<set x='").append(xvalue).append("' y='").append(yvalue).append("' />");
		}
		return datasetxml;
	}
	
	
	/**
	 * 将group中的属性， 生成xml中的属性中，
	 * 
	 * @param group
	 * @param objectXml
	 */
	protected StringBuilder toDataBindingRefGroupAttributeString(PropertyGroup group) {
		StringBuilder objectXml = new StringBuilder();
		for (Property property : group.getProperties()) {
			if (property.getValue() != null && property.isGenerate()) {
				objectXml.append(" ").append(property.getCode()==null?"":property.getCode()).append("='")
				.append(property.getValue()==null?"":property.getValue()).append("' ");
			}
		}
		return objectXml;
	}

}
