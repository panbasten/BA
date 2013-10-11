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
	 * 预测产品-当月预测填报
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String predictSetting(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 预测产品-当月延伸期预测填报
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendSetting(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

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

	/**
	 * 预测评分-上月预测评分填报
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String scoreSetting(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

	/**
	 * 方法评估-月预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredictEvaluate(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 方法评估-延伸期降水过程预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredictPrecipitation(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 分析工具-海温月预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String sstMonthPredict(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 分析工具-海温季预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String sstQuarterPredict(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 制作评分数据-月预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredictData(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 制作评分数据-延伸期预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredictData(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 设置-数据更新
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String dataUpdate(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

	/**
	 * 设置-上传数据
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String dataUpload(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

	/**
	 * 设置-业务定时期
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String buzTimed(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

	/**
	 * 业务规范
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String buzNorms(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

	/**
	 * 相关网站链接
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String links(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

}
