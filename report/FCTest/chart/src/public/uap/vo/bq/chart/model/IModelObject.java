package uap.vo.bq.chart.model;

import java.io.Serializable;

/**
 * ģ�Ͷ���
 * 
 * @author zhanglld
 *
 */
public interface IModelObject extends Cloneable, Serializable{
	boolean isChanged();
	void setChange(boolean changed);
	Object clone() throws CloneNotSupportedException;
//	void setChartCacheData (String key, Object value);
//	Object getChartCacheData (String key);
}
