package uap.vo.bq.chart.model.dataset;

import java.io.Serializable;

public interface IChartDataElement extends Serializable, Cloneable{

	public String getCode();
	public void setCode(String code);
	
	/** ��������xmlչʾ���� */
	public String getCaption();
	public void setCaption (String caption);
	
	/* �жϸõ�Ԫ��ֵ�Ƿ�仯 */
	public boolean isChanged();
	
	public void setChanged (boolean changed);
	
	public Object clone();
	
}
