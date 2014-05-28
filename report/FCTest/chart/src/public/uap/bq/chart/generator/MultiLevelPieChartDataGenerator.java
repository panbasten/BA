package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;

import com.ufida.iufo.pub.tools.AppDebug;
//import uap.bq.chart.generator.BaseChartDataGenerator.DataBindingItemList;

public class MultiLevelPieChartDataGenerator extends LinkChartDataGenerator {

	
	private List<ChartCategoryAxis> catlist;
	private ChartSeriesAxis series;
	//private JsonStack<String> linkstack; 
	//private JsonStack<String> categorystack;
	//private static String linkstart = "javascript:_processLink(\\\"{";
	//private static String linkend   = "}\\\")";
	
	
	@Override
	public void clear() {
		super.clear();
		catlist = null;
		series = null;
		//linkstack = null;
		//categorystack = null;
	}
	
	
	@Override
	public StringBuilder generateLinkChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {
		
	    catlist = new ArrayList<ChartCategoryAxis>();
		for(DataBindingItem dbi : dataBindingItems){
			if(dbi.getChartDataBinding() instanceof ChartCategoryAxis){
				catlist.add( (ChartCategoryAxis) dbi.getChartDataBinding() );
			}else if(dbi.getChartDataBinding() instanceof ChartSeriesAxis){
				series = (ChartSeriesAxis) dbi.getChartDataBinding();
			}
		}
		StringBuilder xml = new StringBuilder();
		//xml.append("<category label='").append(dataset.getChartHeader().getHeaderCell(series.getColumnCode()).getCaption()).append("' >");
		xml.append(generatecategorie(0 , dataset.getChartBody().getChartBodyRows() ));
		//xml.append("</category>");
		return xml;
		
	}
	
	
	private  StringBuilder generatecategorie(int index,ChartBodyRow[] chartBodyRows){
		ChartCategoryAxis category = catlist.get(index);
		StringBuilder xml = new StringBuilder();
		String categorycode = category.getColumnCode();
		boolean categoryisHasOperate = category.isHasOperate();
		if(categoryisHasOperate)
		    categorystack.push(categorycode);
		String seriescode = series.getColumnCode();
		HashMap<ChartDataCell,RowsandSumvalue> slmap = new HashMap<ChartDataCell,RowsandSumvalue>();
		for(ChartBodyRow cbr : chartBodyRows){
			ChartDataCell datacell = cbr.getChartDataCell(categorycode);
			//String categoryvalue = datacell.getCaption();
			
			if(cbr.getChartDataCell(seriescode)==null || 
					cbr.getChartDataCell(seriescode).getCaption()==null || 
					cbr.getChartDataCell(seriescode).getCaption().equals("")){
				AppDebug.debug("A datacell or value is null (in MultiLevelPieChartDataGenerator) !");
				continue;
			}
				
			String value = cbr.getChartDataCell(seriescode).getCaption();
			StringUtils.remove(value, ",");
			double seriesvalue = Double.valueOf(StringUtils.remove(value, ","));
			if(!slmap.containsKey(datacell)) 
				slmap.put(datacell, new RowsandSumvalue());
			slmap.get(datacell).add(cbr,seriesvalue);
		}
		
		for(ChartDataCell key : slmap.keySet()){
			if(categoryisHasOperate)
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(categorycode,key));
				//linkstack.push(JsonCellUtils.getJsonCell(categorycode, key.getAlign()+","+key.getCode()) );
			String value = String.valueOf(slmap.get(key).sumvalue) ;
			value = getScaleValue(value,false);
			xml.append("<category label='").append(key.getCaption())
			    .append("' value='").append(value).append("' ");
			if(series.isHasOperate()){
				ChartDataCell sumkey;
				List<ChartBodyRow> rows = slmap.get(key).chartBodyRows;
				if(rows == null || rows.isEmpty())
					sumkey = null;
				else{
					ChartDataCell datacelltemp = (ChartDataCell)rows.get(0).getChartDataCell(seriescode).clone();
					//sumkey = (SumValueChartDataCell)rows.get(0).getChartDataCell(seriescode).clone();
					sumkey = new SumValueChartDataCell(datacelltemp);
				    sumkey.setCaption(value);
				}
				linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(seriescode,sumkey));
				StringBuilder currentlink = getlink(ValueChartType.VALUE_CHART_SET);
				xml.append(" link='").append(currentlink).append("' ");
				linkSeriesAxisstack.pop();
			}
				
			if(index == catlist.size()-1)xml.append(" />");
			else {
				xml.append(" >");
				xml.append( generatecategorie(index+1,
						slmap.get(key).chartBodyRows.toArray(new ChartBodyRow[slmap.get(key).chartBodyRows.size()]) ));
				xml.append("</category>");
			}
			
			if(categoryisHasOperate)
				linkCategoryAixsstack.pop();
			
		}
		
		if(categoryisHasOperate)
			categorystack.pop();
		
		return xml;
	}
	
	
	protected class SumValueChartDataCell extends ChartDataCell{
		
		public SumValueChartDataCell(ChartDataCell chartDataElement) {
			super(chartDataElement);
			sumvalue = chartDataElement.getCaption();
			isAll = chartDataElement.isAll();
			this.setCanDrillDown(chartDataElement.isCanDrillDown());
			this.setCanDrillUp(chartDataElement.isCanDrillUp());
			this.setPkValue(chartDataElement.getPkValue());
			
		}
		
		public SumValueChartDataCell(String chartHeaderCellCode,ChartDataCell chartDataElement) {
			super(chartHeaderCellCode,chartDataElement);
			sumvalue = chartDataElement.getCaption();
			isAll = chartDataElement.isAll();
			this.setCanDrillDown(chartDataElement.isCanDrillDown());
			this.setCanDrillUp(chartDataElement.isCanDrillUp());
			this.setPkValue(chartDataElement.getPkValue());
		}
		
		private String sumvalue ;
		private boolean isAll;
		
		public boolean isAll(){
			return isAll;
		}
		
		public String getCaption(){
			return sumvalue;
		}
		
		public void setCaption (String caption){
			this.sumvalue = caption;
		}
	
	}
	
	protected class RowsandSumvalue{
		protected List<ChartBodyRow> chartBodyRows =new ArrayList<ChartBodyRow>();
		protected double sumvalue = 0;
		
		public void add(ChartBodyRow cbr,double value){
			chartBodyRows.add(cbr);
			sumvalue += value;
		}
		
		public String getsumvalue(){
			return Double.toString(sumvalue);
			
		}
		
	}
	
	
}
