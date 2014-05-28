package uap.vo.bq.chart.model.databinding;

import uap.vo.bq.chart.model.dataset.IChartDataElement;

public class ChartXYZSeriesAxis extends ChartXYSeriesAxis {

	public ChartXYZSeriesAxis(IChartDataElement chartDataElement) {
		super(chartDataElement);
		// TODO Auto-generated constructor stub
	}

	public ChartXYZSeriesAxis(IChartDataElement chartDataElement,
			String datasetCode, boolean hasOperate) {
		super(chartDataElement, datasetCode, hasOperate);
		// TODO Auto-generated constructor stub
	}
	
	
	private String zColumnCode;
	private String nameColumnCode;
	
	public String getzColumnCode() {
		return zColumnCode;
	}
	public void setzColumnCode(String zColumnCode) {
		this.zColumnCode = zColumnCode;
	}
	public String getNameColumnCode() {
		return nameColumnCode;
	}
	public void setNameColumnCode(String nameColumnCode) {
		this.nameColumnCode = nameColumnCode;
	}

}
