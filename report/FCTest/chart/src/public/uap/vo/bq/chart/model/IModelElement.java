package uap.vo.bq.chart.model;

/**
 * ������ChartModel���Ӷ���
 * 
 * @author zhanglld
 *
 * @param <T> ������
 */
public interface IModelElement<T> extends IModelObject{
	String getCode();
	IModelObject getSuperObject();	
}
