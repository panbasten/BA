package uap.impl.bq.chart.repository;

import uap.impl.bq.chart.cache.GeneralCache;
import nc.vo.pub.format.exception.FormatException;

/**
 * 用于生成4个 Repository 的工程类，
 * @author hbyxl
 *
 */
public class RepositoryFactory {

	private static final String PROPERTYREPOSITORY_CACHEKEY = "PROPERTYREPOSITORY_CACHEKEY";
	private static final String TYPEPROPERTY_CACHEKEY = "TYPEPROPERTY_CACHEKEY";
	private static final String APIPROPERTY_CACHEKEY = "APIPROPERTY_CACHEKEY";
	private static final String EVENTPROPERTY_CACHEKEY = "EVENTPROPERTY_CACHEKEY";
	
	private static final RepositoryFactory instance = new RepositoryFactory();
	
	public static RepositoryFactory getInstance(){
		return instance;
	}
	
	/**
	 * 获得图表属性的所有类型
	 * @return
	 * @throws FormatException
	 */
	public TypeRepository getTypeRepository() throws FormatException{
		
		TypeRepository repository = (TypeRepository) GeneralCache.getInstance(this.getClass().getName()).get(TYPEPROPERTY_CACHEKEY);
		if(repository != null){
			return repository;
		}
		
		repository = TypeRepositoryFactory.getInstance().getTypeRepository();
		GeneralCache.getInstance(this.getClass().getName()).put(TYPEPROPERTY_CACHEKEY, repository);
		return repository;
	}
	
	/**
	 * 获得图表所有属性
	 * @return
	 * @throws FormatException
	 */
	public PropertyRepository getPropertyRepository() throws FormatException{
		
		PropertyRepository repository = (PropertyRepository) GeneralCache.getInstance(this.getClass().getName()).get(PROPERTYREPOSITORY_CACHEKEY);
		if(repository != null){
			return repository;
		}
		
		repository = PropertyRepositoryFactory.getInstance().getPropertyRepository();
		GeneralCache.getInstance(this.getClass().getName()).put(PROPERTYREPOSITORY_CACHEKEY, repository);
		return repository;
		
	}
	
	/**
	 * 获得图表所有事件
	 * @return
	 */
	public EventRepository getEventRepository() {
		
		EventRepository repository = (EventRepository) GeneralCache.getInstance(this.getClass().getName()).get(EVENTPROPERTY_CACHEKEY);
		if(repository != null){
			return repository;
		}
		
		GeneralCache.getInstance(this.getClass().getName()).put(EVENTPROPERTY_CACHEKEY, repository);
		return new EventRepository(null);
	}

	/**
	 * 获得图表所有API接口
	 * @return
	 * @throws FormatException
	 */
	public APIRepository getAPIRepository() {
		
		APIRepository repository = (APIRepository) GeneralCache.getInstance(this.getClass().getName()).get(APIPROPERTY_CACHEKEY);
		if(repository != null){
			return repository;
		}
		
		GeneralCache.getInstance(this.getClass().getName()).put(APIPROPERTY_CACHEKEY, repository);
		return null;
	}

}
