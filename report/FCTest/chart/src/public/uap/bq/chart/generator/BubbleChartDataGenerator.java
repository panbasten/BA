package uap.bq.chart.generator;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import uap.vo.bq.chart.model.databinding.ChartXYSeriesAxis;
import uap.vo.bq.chart.model.databinding.ChartXYZSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;

public class BubbleChartDataGenerator extends ScatterChartDataGenerator {
	protected StringBuilder generatedatasetxml(ChartXYSeriesAxis cxysa_xy,List<ChartBodyRow> chartBodyRows){
		ChartXYZSeriesAxis cxysa = (ChartXYZSeriesAxis) cxysa_xy;
		String xcolumnCode = cxysa.getxColumnCode();
		String ycolumnCode = cxysa.getyColumnCode();
		String zcolumnCode = cxysa.getzColumnCode();   //z轴绑定的行号yColumnCode
		String namecolumnCode = cxysa.getNameColumnCode();   //name轴绑定的行号yColumnCode
		StringBuilder datasetxml = new StringBuilder();
		for (ChartBodyRow cbr : chartBodyRows){
			String xvalue = cbr.getChartDataCell(xcolumnCode).getCaption();
		    String yvalue = cbr.getChartDataCell(ycolumnCode).getCaption();
		    String zvalue = cbr.getChartDataCell(zcolumnCode).getCaption();
		    yvalue = getScaleValue(yvalue,false);
		    zvalue = getScaleValue(zvalue,false);
		    if( StringUtils.isEmpty(xvalue) || StringUtils.isEmpty(yvalue) || StringUtils.isEmpty(zvalue) ) continue;
		    datasetxml.append("<set x='").append(xvalue).append("' y='").append(yvalue).append("' z='").append(zvalue).append("' ");
		    if( cbr.getChartDataCell(namecolumnCode)==null || StringUtils.isEmpty(cbr.getChartDataCell(namecolumnCode).getCaption()) ) 
		    	datasetxml.append(" />");
		    else {
		    	 String namevalue = cbr.getChartDataCell(namecolumnCode).getCaption();
		    	 datasetxml.append(" name='").append(namevalue).append("' />");
		    }
		}
		return datasetxml;
	}

}
