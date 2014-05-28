package uap.vo.bq.chart.model;

import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.DefaultSetting;


public class ChartModelFactory {
	public static ChartModel createChartModel(String chartDefineCode){
		ChartModel chartModel = new ChartModel(chartDefineCode);
		return chartModel;
	}
	
	public static ChartModel createEmptyDataChartModel(ChartDefine define){
		ChartModel chartModel = new ChartModel(define);
		return chartModel;
	}
	
}
