package uap.vo.bq.chart.model.databinding;

import uap.vo.bq.chart.model.dataset.IChartDataElement;

public class ChartCategoryAxis extends ChartDataBinding {

	private static final long serialVersionUID = 1L;

	public ChartCategoryAxis(IChartDataElement chartDataElement,
			String datasetCode, boolean hasOperate) {
		super(chartDataElement, datasetCode, hasOperate);
		// TODO Auto-generated constructor stub
	}
	
	public ChartCategoryAxis(IChartDataElement chartDataElement) {
		super(chartDataElement, null, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return super.clone();
	}

	
}
