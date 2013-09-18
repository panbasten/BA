package com.flywet.cust.p001.portal;

import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.delegates.model.BIAdaptorInterface;

public interface ForecastAdaptor extends BIAdaptorInterface {

	/**
	 * 预测产品-月预测
	 * 
	 * @param targetId
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredict(String targetId) throws BIJSONException;

	/**
	 * 预测产品-延伸期预测
	 * 
	 * @param targetId
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredict(String targetId) throws BIJSONException;

	/**
	 * 预测评分-月预测
	 * 
	 * @param targetId
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredictScore(String targetId) throws BIJSONException;

	/**
	 * 预测评分-延伸期预测
	 * 
	 * @param targetId
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredictScore(String targetId) throws BIJSONException;

}
