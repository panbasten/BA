package uap.vo.bq.chart.model.databinding;

import uap.vo.bq.chart.model.OrderEnum;
import uap.vo.bq.chart.model.dataset.AbsChartDataElementProxy;
import uap.vo.bq.chart.model.dataset.IChartDataElement;

public class ChartDataBinding extends AbsChartDataElementProxy implements IChartDataBinding {

	private static final long serialVersionUID = 1L;
	private boolean hasOperate = true;
	private String datasetCode ;
	private String columnCode;
	private OrderEnum order;

	public ChartDataBinding(IChartDataElement chartDataElement) {
		super(chartDataElement);
	}
	
	public ChartDataBinding(IChartDataElement chartDataElement, String datasetCode, boolean hasOperate) {
		super(chartDataElement);
		this.hasOperate = hasOperate;
		this.datasetCode = datasetCode;
	}

	@Override
	public void setColumnCode(String columnCode) {
		// TODO Auto-generated method stub
		this.columnCode = columnCode;
	}

	@Override
	public String getColumnCode() {
		// TODO Auto-generated method stub
		return columnCode;
	}

	@Override
	public void setDatasetCode(String datasetCode) {
		this.datasetCode = datasetCode;
	}

	@Override
	public String getDatasetCode() {
		return datasetCode;
	}

	@Override
	public void setHasOperate(boolean isHasOperate) {
		this.hasOperate = isHasOperate;
	}

	@Override
	public boolean isHasOperate() {
		return hasOperate;
	}

	@Override
	public Object clone() {
		ChartDataBinding copy = (ChartDataBinding) super.clone();
		copy.datasetCode = this.datasetCode;
		copy.hasOperate = this.hasOperate;
		return copy;
	}

	public OrderEnum getOrder() {
		return order;
	}

	public void setOrder(OrderEnum order) {
		this.order = order;
		setChanged(true);
	}
}
