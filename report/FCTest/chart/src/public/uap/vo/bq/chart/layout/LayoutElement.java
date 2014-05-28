package uap.vo.bq.chart.layout;

import java.io.Serializable;

/**
 * ²¼¾ÖÔªËØ
 * 
 * @author zhanglld
 *
 */
public interface LayoutElement extends Serializable, Cloneable {

	public String getName();

	public void setName(String name);

	public Object clone();
}