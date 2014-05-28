package uap.vo.bq.chart.model;

/**
 * 适用于ChartModel的子对象
 * 
 * @author zhanglld
 *
 * @param <T> 父对象
 */
public interface IModelElement<T> extends IModelObject{
	String getCode();
	IModelObject getSuperObject();	
}
