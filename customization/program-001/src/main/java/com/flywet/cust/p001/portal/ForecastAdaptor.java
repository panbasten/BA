package com.flywet.cust.p001.portal;

import java.util.HashMap;

import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.delegates.model.BIAdaptorInterface;

public interface ForecastAdaptor extends BIAdaptorInterface {

	/**
	 * 预测产品-月预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredict(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

	/**
	 * 预测产品-月预测，更新月份
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredictUpdate(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 预测产品-延伸期预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredict(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

	/**
	 * 预测产品-延伸期预测，更新旬
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredictUpdate(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 预测评分-月预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredictScore(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 预测评分-延伸期预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredictScore(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

}
