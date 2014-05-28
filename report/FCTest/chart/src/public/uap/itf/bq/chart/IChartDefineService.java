package uap.itf.bq.chart;

import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.ChartDefineDigest;

/**
 * 统计图定义服务 该服务只供cache 使用
 * @author zhanglld
 *
 */
public interface IChartDefineService {
	/**
	 * 根据统计图定义编码获得统计图定义
	 * 
	 * @param chartDefineCode 统计图定义编码
	 * @return 统计图定义
	 */
	public ChartDefine getChartDefine(String chartDefineCode);
	
	/**
	 * 根据多个统计图定义编码获得多个统计图定义对象
	 * 
	 * @param chartDefineCodes
	 * @return 统计图定义数组
	 */
	public ChartDefine[] getChartDefines(String[] chartDefineCodes);
	
	/**
	 * 获得当前所有的统计图摘要
	 * 
	 * @return 统计图定义摘要数组
	 */
	public ChartDefineDigest[] getChartDefineDigests();
}
