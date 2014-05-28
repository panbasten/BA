package uap.vo.bq.chart.model.databinding;

import uap.vo.bq.chart.model.dataset.IChartDataElement;

public class ChartXYSeriesAxis extends ChartDataBinding {
	
	public ChartXYSeriesAxis(IChartDataElement chartDataElement) {
		super(chartDataElement);
	}
	//
	public ChartXYSeriesAxis(IChartDataElement chartDataElement, String datasetCode, boolean hasOperate) {
		super(chartDataElement, datasetCode,hasOperate);
	}

	private String xColumnCode;//x轴绑定那一行
	private String xColumnType;
	private String yColumnCode;
	
	
	
	public String getxColumnCode() {
		return xColumnCode;
	}

	public void setxColumnCode(String xColumnCode) {
		this.xColumnCode = xColumnCode;
	}

	public String getxColumnType() {
		return xColumnType;
	}

	public void setxColumnType(String xColumnType) {
		this.xColumnType = xColumnType;
	}

	public String getyColumnCode() {
		return yColumnCode;
	}

	public void setyColumnCode(String yColumnCode) {
		this.yColumnCode = yColumnCode;
	}
	
	
}
