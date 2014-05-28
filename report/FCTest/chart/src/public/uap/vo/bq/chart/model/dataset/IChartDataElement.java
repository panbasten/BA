package uap.vo.bq.chart.model.dataset;

import java.io.Serializable;

public interface IChartDataElement extends Serializable, Cloneable{

	public String getCode();
	public void setCode(String code);
	
	/** 用于生成xml展示数据 */
	public String getCaption();
	public void setCaption (String caption);
	
	/* 判断该单元格值是否变化 */
	public boolean isChanged();
	
	public void setChanged (boolean changed);
	
	public Object clone();
	
}
