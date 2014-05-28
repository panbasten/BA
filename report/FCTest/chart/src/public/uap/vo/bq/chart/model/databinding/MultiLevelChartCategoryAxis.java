package uap.vo.bq.chart.model.databinding;

import uap.vo.bq.chart.model.dataset.IChartDataElement;

public class MultiLevelChartCategoryAxis extends ChartCategoryAxis {
	
	public MultiLevelChartCategoryAxis(IChartDataElement chartDataElement,
			String datasetCode, boolean hasOperate) {
		super(chartDataElement, datasetCode, hasOperate);
		// TODO Auto-generated constructor stub
	}

	public MultiLevelChartCategoryAxis(IChartDataElement chartDataElement) {
		super(chartDataElement);
		// TODO Auto-generated constructor stub
	}
	
	private MultiLevelChartCategoryAxis sonCategoryAxis = null;

	public MultiLevelChartCategoryAxis getSonCategoryAxis() {
		return sonCategoryAxis;
	}

	public void setSonCategoryAxis(MultiLevelChartCategoryAxis sonCategoryAxis) {
		this.sonCategoryAxis = sonCategoryAxis;
	}

}
