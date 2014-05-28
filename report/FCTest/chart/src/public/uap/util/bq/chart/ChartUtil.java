package uap.util.bq.chart;


import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

import com.ufida.iufo.pub.tools.AppDebug;

import uap.bq.chart.generator.ChartGenerateException;
import uap.bq.chart.generator.DataGenerateResult;
import uap.bq.chart.generator.DataGenerator;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartDataset;


/**
 * 生成显示用的数据
 * 
 * @author zll
 * 
 */
public class ChartUtil {
	
	
	/**
	 * 生成显示用的数据
	 * 
	 * @param model
	 * @param datasets
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public static DataGenerateResult generateDisplayData(ChartDefine define,ChartModel model)
			throws ChartGenerateException, ClassNotFoundException {
		
		DataGenerator dataGenerator = DataGenerator.getInstance(model.getChartDefineCode());
		if (null != dataGenerator){
			long t = System.currentTimeMillis();
			DataGenerateResult res = dataGenerator.generate(0, define, model);
			return res;
		}
		return null;
	}

	public static DataGenerateResult generateDisplayData(ChartDefine define,
			ChartModel model, boolean enableAnimation)
			throws ChartGenerateException, ClassNotFoundException {
		
		DataGenerator dataGenerator = DataGenerator.getInstance(model.getChartDefineCode());
		if (null != dataGenerator){
			long t = System.currentTimeMillis();
			DataGenerateResult res = dataGenerator.generate(enableAnimation?2:1,define, model);
			AppDebug.debug("## generateDisplayData cost time: " + (System.currentTimeMillis() - t) + "ms.");
			return res;
		}
		return null;
	}
	
	public static DataGenerateResult generateChartFaceData(ChartDefine define,
			ChartModel model)
			throws ChartGenerateException, ClassNotFoundException {
		long t = System.currentTimeMillis();
		DataGenerator dataGenerator = DataGenerator.getInstance(model.getChartDefineCode());
		if (null != dataGenerator){
			DataGenerateResult res = dataGenerator.generate(3,define, model);
			AppDebug.debug("## generateChartFaceData cost time: " + (System.currentTimeMillis() - t) + "ms.");
			return res;
		}
		return null;
	}

	public static boolean isEmptyDatasets(List<ChartDataset> datasets) {
		boolean bEmptyDataset = true;
		if (datasets != null) {
			for (ChartDataset ds : datasets) {
				bEmptyDataset &= isEmptyDataset(ds);
			}
		}
		return bEmptyDataset;
	}
	
	public static boolean isEmptyDatasets(ChartDataset [] datasets) {
		boolean bEmptyDataset = true;
		if (datasets != null) {
			for (ChartDataset ds : datasets) {
				bEmptyDataset &= isEmptyDataset(ds);
			}
		}
		return bEmptyDataset;
	}

	public static boolean isEmptyDataset(ChartDataset dataset) {
		return dataset == null || dataset.isEmpty();
	}
	
	/**
	 * 制作简明索引字符串
	 * 
	 * @param nbreakCount
	 * @param key
	 * @return
	 */
	public static String toCutOutString(String value, int spltCount, String spltChar) {
		int splt = 0;
		for (int index = 0; index < spltCount; ++index) {
			splt = value.indexOf(spltChar, splt);
			splt++;
		}
		if (splt != -1) {
			return value.substring(splt, value.length());
		}
		return null;
	}

	/**
	 * 判断给定字符串是否为真
	 * 
	 * @param value
	 * @param defaultBoolean
	 * @return
	 */
	public static boolean isTrue(String value, boolean defaultBoolean) {
		if (value == null) {
			return defaultBoolean;
		} else {
			String newValue = value.toLowerCase();
			if (newValue.equals("y") || newValue.equals("t")
					|| newValue.equals("1") || newValue.equals("true")) {
				return true;
			} else if (newValue.equals("n") || newValue.equals("f")
					|| newValue.equals("0") || newValue.equals("false")) {
				return false;
			} else {
				return defaultBoolean;
			}
		}
	}
	
	/**
	 * 判断给定绑定轴是否为分类轴
	 * 
	 * @param dbg
	 * @return
	 */
	public static boolean isCategoryAxisObject(IChartDataBinding dbg) {
		if (dbg != null && dbg instanceof ChartCategoryAxis) {
			return true;
		}
		return false;
	}

	/**
	 * 判断给定绑定轴是否为系列轴
	 * 
	 * @param dbg
	 * @return
	 */
	public static boolean isSeriesAxisObject(IChartDataBinding dbg) {
		if (dbg != null && dbg instanceof ChartSeriesAxis) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断给定绑定轴是否为系列轴
	 * 
	 * @param dbg
	 * @return
	 */
	public static boolean isMesuerAxisObject(IChartDataBinding dbg) {
		if (dbg != null && dbg instanceof ChartSeriesAxis) {
			return ((ChartSeriesAxis)dbg).isMeasure();
		}
		return false;
	}

	public static String FromLangRes (Element elem, String name){
		if (elem == null)
			return null;
		Attribute objAttri = elem.attribute(name);

		if (objAttri == null)
			return null;

		String nameid = objAttri.getText();
		if (nameid !=null && nameid.startsWith("{") && nameid.endsWith("}")){
			nameid = nameid.replace("{", "").replace("}", "");
			int pos = nameid.indexOf("=");
			if (pos != -1){
				nameid = nameid.substring(0,pos);
				
			}
			else{
				AppDebug.debug("warnning: FromLangRes found bug, please check fix the issue at " + nameid);
			}
			return nameid;//nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0",nameid);
		}
		else{
			return nameid;
		}
	}
}
