package uap.itf.bq.chart;

import nc.vo.pub.format.exception.FormatException;
import uap.vo.bq.chart.layout.Layout;

/**
 * 布局服务  该服务只供 cache 使用
 * 
 * @author zhanglld
 *
 */
public interface ILayoutService {
	/**
	 * 根据统计图定义编码获得布局对象
	 * 
	 * @param chartDefineCode
	 * @return
	 * @throws FormatException
	 */
	Layout getLayout(String chartDefineCode) throws FormatException;
}
