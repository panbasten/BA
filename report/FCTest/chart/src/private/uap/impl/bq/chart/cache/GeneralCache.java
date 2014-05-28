package uap.impl.bq.chart.cache;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * ����
 * �Ժ�Ӧ����ϵͳ����ӿ�
 * 
 * @author zhanglld
 *
 */
public class GeneralCache {
	private static Hashtable<String, GeneralCache> cacheMap
			= new Hashtable<String, GeneralCache>();
	
	/* ����ʵ������ */
	private Hashtable<String, Serializable> dataTable = new Hashtable<String, Serializable>();
	
	/**
	 * ������
	 * @param type �������ͣ�ÿ����ģ��ɶ����Լ��Ļ�������
	 */
	private GeneralCache(){
	}
	
	/**
	 * ���ݻ������ͻ�û���ʵ��
	 * @param type �������ͣ�ÿ����ģ��ɶ����Լ��Ļ�������
	 * @return ����
	 */
	public static GeneralCache getInstance(String type){
		GeneralCache cache = cacheMap.get(type);
		
		if(cache == null){
			cache = new GeneralCache();
			cacheMap.put(type, cache);
		}
		
		return cache;
	}
	
	/**
	 * ���뻺��
	 * @param key
	 * @param value
	 */
	public void put(String key, Serializable value){
		dataTable.put(key, value);
	}
	
	/**
	 * ��û���
	 * @param key
	 * @return
	 */
	public Object get(String key){
		return dataTable.get(key);
	}
	
	/**
	 * �ӻ������Ƴ�
	 * @param key
	 * @return
	 */
	public Serializable remove(String key){
		return dataTable.remove(key);
	}
}
