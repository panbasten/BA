package uap.vo.bq.chart.model.databinding;

import java.util.ArrayList;
import java.util.List;

import uap.vo.bq.chart.model.OrderEnum;
import uap.vo.bq.chart.model.dataset.IChartDataElement;

public class ChartStackedSeriesAxis extends ChartDataBinding {

	private static final long serialVersionUID = 1L;
	private String name;
	
	private List<IChartDataBinding> chartDataBindings = new ArrayList<IChartDataBinding>();
	
	public ChartStackedSeriesAxis(IChartDataElement chartDataElement) {
		super(chartDataElement);
		// TODO Auto-generated constructor stub
	}

	public void setName (String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void add (ChartDataBinding dbg){
		chartDataBindings.add(dbg);
	}
	
	public void add (List<IChartDataBinding> dbgs){
		chartDataBindings.addAll(dbgs);
	}
	
	public IChartDataBinding get (int index){
		if (index >=0 && index < size()){
			return chartDataBindings.get(index);
		}
		return null;
	}
	
	public int size(){
		return chartDataBindings.size();
	}
	
	public ChartDataBinding[] toArray(){
		return chartDataBindings.toArray(new ChartDataBinding[chartDataBindings.size()]);
	}

	@Override
	@Deprecated
	public void setColumnCode(String columnCode) {
		// TODO Auto-generated method stub
		super.setColumnCode(columnCode);
	}

	@Override
	@Deprecated
	public String getColumnCode() {
		// TODO Auto-generated method stub
		return super.getColumnCode();
	}

	@Override
	@Deprecated
	public void setDatasetCode(String datasetCode) {
		// TODO Auto-generated method stub
		super.setDatasetCode(datasetCode);
	}

	@Override
	@Deprecated
	public String getDatasetCode() {
		// TODO Auto-generated method stub
		return super.getDatasetCode();
	}

	@Override
	@Deprecated
	public void setHasOperate(boolean isHasOperate) {
		// TODO Auto-generated method stub
		super.setHasOperate(isHasOperate);
	}

	@Override
	@Deprecated
	public boolean isHasOperate() {
		// TODO Auto-generated method stub
		return super.isHasOperate();
	}

	@Override
	@Deprecated
	public OrderEnum getOrder() {
		// TODO Auto-generated method stub
		return super.getOrder();
	}

	@Override
	@Deprecated
	public void setOrder(OrderEnum order) {
		// TODO Auto-generated method stub
		super.setOrder(order);
	}

}
