package com.flywet.platform.bi.cust.p001.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.fileupload.FileItem;

import com.flywet.platform.bi.core.exception.BIException;
import com.flywet.platform.bi.core.exception.BIJSONException;
import com.flywet.platform.bi.core.model.ParameterContext;
import com.flywet.platform.bi.services.intf.BIRepositoryDelegates;

public interface ForecastDelegates extends BIRepositoryDelegates {

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

    public String extendSettingUpdateSelect(String targetId,
                                            HashMap<String, Object> context) throws BIJSONException;

    public String metroPortal(String targetId,
                              HashMap<String, Object> context) throws BIJSONException;

    public String editNotes(String targetId,
                            HashMap<String, Object> context) throws BIException;

    public String editNotesSave(String targetId,
                                ParameterContext context) throws BIException;

    public String editNotesChange(String targetId,
                                  HashMap<String, Object> context) throws BIException;

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
	 * 预测产品-延伸期预测-当月更新行为
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendSettingUpdate(String targetId, ParameterContext context)
			throws BIJSONException;

	/**
	 * 预测产品-当月预测填报，更新
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String predictSettingUpdate(String targetId,
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
	 * 预测评分-上月预测评分更新
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String scoreSettingUpdate(String targetId, ParameterContext context)
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
	 * 分析工具-海温月预测-预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String sstMonthPredictRun(String targetId,
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
	 * 分析工具-海温季预测-预测
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String sstQuarterPredictRun(String targetId,
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
	 * 制作评分数据-月预测-执行
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredictDataRun(String targetId,
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
	 * 制作评分数据-延伸期预测-执行
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredictDataRun(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 分析工具-数据更新
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String dataUpdate(String targetId, HashMap<String, Object> context)
			throws BIJSONException;

	/**
	 * 分析工具-数据更新-更新文件
	 * 
	 * @param items
	 * @param dataObj
	 * @return
	 * @throws BIJSONException
	 */
	public String dataUpdateFiles(ArrayList<FileItem> items,
			HashMap<String, String> dataObj) throws BIJSONException;

    /**
     * 设置-数据展现（上传数据）
     * @param targetId
     * @param context
     * @return
     * @throws BIJSONException
     */
    public String dataShow(String targetId, HashMap<String, Object> context)
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
	 * 业务规范更新
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String buzNormsUpdate(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

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

	/**
	 * 方法评估-上月预测评估填报-打开页面
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredictEvaluateSetting(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 方法评估-上月预测评估填报-上传文件更新页面
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredictEvaluateSettingUpdate(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 方法评估-上月预测评估填报-提交
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String monthPredictEvaluateSettingSubmit(String targetId,
			ParameterContext context) throws BIJSONException;

	/**
	 * 方法评估-上月延伸期降水过程预测评估填报-打开页面
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredictPrecipitationSetting(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

	/**
	 * 方法评估-上月延伸期降水过程预测评估填报-提交
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredictPrecipitationSettingSubmit(String targetId,
			ParameterContext context) throws BIJSONException;

	/**
	 * 制作评分数据-上报数据-月预测评分数据上传
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIException
	 */
	public String monthPredictDataUpload(String targetId,
			HashMap<String, Object> context) throws BIException;

	/**
	 * 制作评分数据-上报数据-延伸期预测评分数据上传
	 * 
	 * @param targetId
	 * @param context
	 * @return
	 * @throws BIJSONException
	 */
	public String extendPredictDataUpload(String targetId,
			HashMap<String, Object> context) throws BIJSONException;

}
