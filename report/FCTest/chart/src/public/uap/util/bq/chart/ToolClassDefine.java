package uap.util.bq.chart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ufida.iufo.pub.tools.AppDebug;

import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.dataset.ChartDataset;
/**
 * �����ඨ��
 * 
 * @author wangqzh
 *
 */
public class ToolClassDefine {
	/**
	 * �ַ����б���
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class StringList extends ArrayList<String> {
		private static final long serialVersionUID = 1L;
	}
	/**
	 * �ַ�����������ӳ�����
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class StringObjectMap extends HashMap<String, Object> {

		private static final long serialVersionUID = 1L;
	}

	/**
	 * ��������б���
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class ObjectList extends ArrayList<Object> {
		private static final long serialVersionUID = 1L;
	}
	/**
	 * �������ӳ����
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class ObjectToObjectMap extends HashMap<Object, Object> {

		private static final long serialVersionUID = 1L;
	}

	/**
	 * �ַ��������ַ�ӳ����
	 * 
	 * @author wangqzh
	 * 
	 */
	public static class StringToStringMap extends HashMap<String, String> {

		private static final long serialVersionUID = 1L;
	}
	/**
	 * ����������ӳ����
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
	 * �����������
	 * @author wangqzh
	 *
	 */
	public static class DataBindingPropertyMap extends HashMap<String, DataBindingRefGroup>{
		private static final long serialVersionUID = 1L;
		
	}
	/**
	 * ���Թ���ִ��ʱ��ķ���
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
	 * �ַ�������ӳ�䵽���ݼ�
	 * @author wangqzh
	 *
	 */
	public static class CodeToChartDatasetMap extends HashMap<String,ChartDataset>{
		private static final long serialVersionUID = 1L;
		
	}
	
}
