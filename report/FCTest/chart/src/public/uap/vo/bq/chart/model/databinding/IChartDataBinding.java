package uap.vo.bq.chart.model.databinding;

import uap.vo.bq.chart.model.OrderEnum;
import uap.vo.bq.chart.model.dataset.IChartDataElement;

/**
 * 数据绑定接口
 * @author wangqzh
 *
 */
public interface IChartDataBinding  extends IChartDataElement {
	public void setHasOperate (boolean isHasOperate);
	public boolean isHasOperate();
	public void setDatasetCode (String datasetCode);
	public String getDatasetCode ();
	public void setColumnCode (String columnCode);
	public String getColumnCode ();
	public OrderEnum getOrder();
	public void setOrder(OrderEnum order) ;
}
