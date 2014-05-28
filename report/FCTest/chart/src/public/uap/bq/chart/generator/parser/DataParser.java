package uap.bq.chart.generator.parser;

import java.util.HashMap;
import java.util.Map;

import nc.vo.pub.format.exception.FormatException;

import org.dom4j.Element;

import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.define.DefaultSetting;

import com.ufida.iufo.pub.tools.AppDebug;

public abstract class DataParser {
	private static Map<String, DataParser> instanceMap = new HashMap<String, DataParser>();
	private static Map<String, String> registedCode = new HashMap<String, String>();
	private static boolean inited = false;
	
	private static void init() {
		if (!inited) {
			registe("MSBar2D",   	   "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("MSColumn2D",      "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("Radar",   	       "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("RadarArea",   	   "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("RadarLine",   	   "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("RadarPoint",      "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("MSArea",          "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("MSLine",          "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("Marimekko",       "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("Pie2D",           "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("Waterfall2D",           "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("Pareto2D",        "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("Funnel",          "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("Pyramid",          "uap.bq.chart.generator.parser.DefaultSettingParser");
			
			registe("Bubble",               "uap.bq.chart.generator.parser.XYZChartDefaultSettingParser");
			registe("Scatter",              "uap.bq.chart.generator.parser.XYChartDefaultSettingParser");
			registe("MSStackedColumn2D",   "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("MultiLevelPie",       "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("SparkColumn",   	   "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("SparkWinLoss",   	   "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("SparkLine",   	       "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("MultiAxisLine",   	   "uap.bq.chart.generator.parser.MultiLevelSeriesDefaultSettingParser");
			registe("MSCombiDY2D",   	  "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("MSCombi2D",   	  "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("StackedColumn2DLine",   	  "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("StackedColumn2D",   	  "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("AngularGauge",    "uap.bq.chart.generator.parser.AngularGaugeDefaultSettingParser");
			registe("HLinearGauge",    "uap.bq.chart.generator.parser.AngularGaugeDefaultSettingParser");
			registe("VBullet",    "uap.bq.chart.generator.parser.AngularGaugeDefaultSettingParser");
			registe("HBullet",    "uap.bq.chart.generator.parser.AngularGaugeDefaultSettingParser");
			registe("Thermometer",    "uap.bq.chart.generator.parser.DefaultSettingParser");
			registe("Cylinder",    "uap.bq.chart.generator.parser.DefaultSettingParser");
			
			inited = true;
		}
	}
	
	private static void registe (String code, String implClass){
		registedCode.put(code, implClass);
	}
	/**
	 * 工厂接口
	 * @param implClass
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static DataParser getInstance(String code) throws ClassNotFoundException{
		
		init();
		
		String implClass = registedCode.get(code);
		if (implClass == null || implClass.isEmpty()){
			AppDebug.debug("No register the code in code hash map!");
			return null;
		}
		DataParser generator = instanceMap.get(implClass);
		if(generator == null){
			try {
				Class<?> clazz = Class.forName(implClass);
				generator = (DataParser) clazz.newInstance();
				instanceMap.put(implClass, generator);
			} catch (InstantiationException e) {
				AppDebug.error(e);
			} catch (IllegalAccessException e) {
				AppDebug.error(e);
			}
			catch (Exception e){
				AppDebug.error(e);
			}
			
		}
		
		return generator;
	}
	
	/**
	 * 获取指定节点的指定属性值
	 * @param elem
	 * @param attri
	 * @return String 值对象
	 */
	protected static String getAttributeValue(Element elem, String attri) {
//		if (elem == null)
//			return null;
//		Attribute objAttri = elem.attribute(attri);
//
//		if (objAttri == null)
//			return null;

		return ChartUtil.FromLangRes(elem, attri);//.getText();
	}
	
	public abstract DefaultSetting parserDefaultSetting(Element elemDefault) throws FormatException;
}
