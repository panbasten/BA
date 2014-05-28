// ChartDefineServiceImpl.java
package uap.impl.bq.chart.service;

import nc.vo.pub.format.exception.FormatException;
import uap.impl.bq.chart.layout.LayoutFactory;
import uap.itf.bq.chart.ILayoutService;
import uap.vo.bq.chart.layout.Layout;

/**
 * 布局服务 该服务只供 cache 使用
 * 
 * @author zhanglld
 *
 */
public class LayoutServiceImpl implements ILayoutService{

	@Override
	public Layout getLayout(String chartDefineCode) throws FormatException {
		LayoutFactory factory = LayoutFactory.getInstance();
		return factory.getLayout(chartDefineCode);
	}
}
