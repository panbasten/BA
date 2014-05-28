package uap.impl.bq.chart.cache;

import java.io.Serializable;
import java.util.Hashtable;

/**
 * 缓存
 * 以后应调用系统缓存接口
 * 
 * @author zhanglld
 *
 */
public class GeneralCache {
	private static Hashtable<String, GeneralCache> cacheMap
			= new Hashtable<String, GeneralCache>();
	
	/* 缓存实际内容 */
	private Hashtable<String, Serializable> dataTable = new Hashtable<String, Serializable>();
	
	/**
	 * 构造器
	 * @param type 缓存类型，每个子模块可定义自己的缓存类型
	 */
	private GeneralCache(){
	}
	
	/**
	 * 根据缓存类型获得缓存实例
	 * @param type 缓存类型，每个子模块可定义自己的缓存类型
	 * @return 缓存
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
	 * 进入缓存
	 * @param key
	 * @param value
	 */
	public void put(String key, Serializable value){
		dataTable.put(key, value);
	}
	
	/**
	 * 获得缓存
	 * @param key
	 * @return
	 */
	public Object get(String key){
		return dataTable.get(key);
	}
	
	/**
	 * 从缓存中移除
	 * @param key
	 * @return
	 */
	public Serializable remove(String key){
		return dataTable.remove(key);
	}
}
