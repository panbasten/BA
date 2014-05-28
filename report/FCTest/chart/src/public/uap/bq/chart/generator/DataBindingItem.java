package uap.bq.chart.generator;

import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.IChartDataElement;

public class DataBindingItem implements Cloneable {
	private int position;
	private IChartDataBinding axis;
	private int column;
	private IChartDataElement chartDataCell = null;

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public IChartDataBinding getChartDataBinding() {
		return axis;
	}

	public void setAxis(IChartDataBinding axis) {
		this.axis = axis;
	}

	public IChartDataElement getChartDataElement() {
		return chartDataCell;
	}

	public void setChartDataElement(IChartDataElement chartDataCell) {
		this.chartDataCell = chartDataCell;
	}

	public DataBindingItem(int position, IChartDataBinding axis, IChartDataElement chartDataCell) {
		this.position = position;
		this.axis = axis;
		//this.column = column;
		this.chartDataCell = chartDataCell;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{").append(position).append(", ").append(axis.toString()).append(", ").append(column).append(", ").append(chartDataCell.getCaption()).append("}");
		return sb.toString();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		DataBindingItem newDataBindingItem = (DataBindingItem) super.clone();
		newDataBindingItem.position = this.position;
		if (this.axis != null){
			newDataBindingItem.axis = (IChartDataBinding) this.axis.clone();
		}
		if (this.chartDataCell != null){
			newDataBindingItem.chartDataCell = (IChartDataElement) this.chartDataCell.clone();
		}
		newDataBindingItem.column = this.column;
		return newDataBindingItem;
	}
	
	
}
