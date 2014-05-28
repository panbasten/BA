package uap.vo.bq.chart.model.databinding;

import uap.vo.bq.chart.model.dataset.IChartDataElement;

public class MultiLevelChartSeriesAxis extends ChartSeriesAxis {

	public MultiLevelChartSeriesAxis(IChartDataElement chartDataElement,
			String datasetCode, boolean hasOperate) {
		super(chartDataElement, datasetCode, hasOperate);
		// TODO Auto-generated constructor stub
	}

	public MultiLevelChartSeriesAxis(IChartDataElement chartDataElement) {
		super(chartDataElement);
		// TODO Auto-generated constructor stub
	}
	
	private String axiscode;

	public String getAxiscode() {
		return axiscode;
	}

	public void setAxiscode(String axiscode) {
		this.axiscode = axiscode;
	}

}
