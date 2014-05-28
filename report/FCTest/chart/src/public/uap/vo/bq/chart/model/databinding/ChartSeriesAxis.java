package uap.vo.bq.chart.model.databinding;

import uap.vo.bq.chart.model.dataset.IChartDataElement;

public class ChartSeriesAxis extends ChartDataBinding {

	private static final long serialVersionUID = 1L;
	private boolean isMeasure = true;

	public ChartSeriesAxis(IChartDataElement chartDataElement,
			String datasetCode, boolean hasOperate) {
		super(chartDataElement, datasetCode, hasOperate);
		// TODO Auto-generated constructor stub
	}
	
	public ChartSeriesAxis(IChartDataElement chartDataElement) {
		super(chartDataElement, null, false);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 是否为指标判断
	 * @return
	 */
	public boolean isMeasure() {
		return isMeasure;
	}

	/**
	 * 
	 * @param isMeasure
	 */
	public void setMeasure(boolean isMeasure) {
		this.isMeasure = isMeasure;
	}

	@Override
	public Object clone() {
		ChartSeriesAxis newChartSeriesAxis = null;
		newChartSeriesAxis = (ChartSeriesAxis)super.clone();
		newChartSeriesAxis.isMeasure = this.isMeasure;
		
		return newChartSeriesAxis;
	}


}
