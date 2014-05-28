package uap.util.bq.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ufida.iufo.pub.tools.AppDebug;

import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.dataset.ChartDataset;
/**
 * 工具类定义
 * 
 * @author wangqzh
 *
 */
public class ToolClassDefine {
	/**
	 * 字符串列表类
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class StringList extends ArrayList<String> {
		private static final long serialVersionUID = 1L;
	}
	/**
	 * 字符串索引对象映射表类
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class StringObjectMap extends HashMap<String, Object> {

		private static final long serialVersionUID = 1L;
	}

	/**
	 * 任意对象列表类
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class ObjectList extends ArrayList<Object> {
		private static final long serialVersionUID = 1L;
	}
	/**
	 * 任意对象映射类
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class ObjectToObjectMap extends HashMap<Object, Object> {

		private static final long serialVersionUID = 1L;
	}

	/**
	 * 字符串索引字符映射类
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class StringToStringMap extends HashMap<String, String> {

		private static final long serialVersionUID = 1L;
	}
	/**
	 * 任意对象类表映射类
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class ObjectMapList {
		private List<Object> valueList = new ArrayList<Object>();
		private Map<Object, Object> valueMap = new HashMap<Object, Object>();

		public void add(Object key, Object value) {
			valueList.add(key);
			valueMap.put(key, value);
		}

		public Object getKey(int index) {
			return valueList.get(index);
		}

		public Object get(Object key) {
			return valueMap.get(key);
		}

		public Object get(int index) {
			Object key = valueList.get(index);
			if (key != null) {
				return get(key);
			}
			return null;
		}

		public int size() {
			return valueList.size();
		}

		public void clear() {
			valueList.clear();
			valueMap.clear();
		}
	}
	/**
	 * 数据外观属性
	 * @author wangqzh
	 *
	 */
	public static class DataBindingPropertyMap extends HashMap<String, DataBindingRefGroup>{
		private static final long serialVersionUID = 1L;
		
	}
	/**
	 * 测试过程执行时间的方法
	 * @author wangqzh
	 *
	 */
	public static class RunableCodeTimeAccount{
		double beginTime = 0;
		String title = "";
		public RunableCodeTimeAccount (String label){
			beginTime = System.currentTimeMillis();
			title = label;
		}
		
		double getRunTime(){
			return System.currentTimeMillis() - beginTime;
		}
		public void printRunTime (){
			AppDebug.debug(title + getRunTime()+"ms.");
		}
	}
	
	/**
	 * 字符串索引映射到数据集
	 * @author wangqzh
	 *
	 */
	public static class CodeToChartDatasetMap extends HashMap<String,ChartDataset>{
		private static final long serialVersionUID = 1L;
		
	}
	
}
