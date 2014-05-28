package uap.bq.chart.generator;

import java.util.HashMap;
import java.util.Map;

import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.model.ChartModel;

import com.ufida.iufo.pub.tools.AppDebug;


/**
 * 负责产生FusionCharts的data.xml或者其他图形需要的Json等
 * 
 * @author zhanglld，Modify by Wangqzh
 */
public abstract class DataGenerator {

	private static Map<String, DataGenerator> instanceMap = new HashMap<String, DataGenerator>();
	private static Map<String, String> registedCode = new HashMap<String, String>();
	
	private static boolean inited = false;
	
	private static void registe (String code, String implClass){
		ClassLoader cl = DataGenerator.class.getClassLoader();
		registedCode.put(code, implClass);
	}
	
	
	private static void init() {
		if (!inited) {
			registe("StackedColumn2D",         	"uap.bq.chart.generator.MultiSeriesChartDataGenerator");
			registe("StackedColumn2DLine",     	"uap.bq.chart.generator.MultiSeriesChartDataGenerator");
			registe("MSCombi2D",     			"uap.bq.chart.generator.MultiSeriesChartDataGenerator");
			registe("MSCombiDY2D",   			"uap.bq.chart.generator.MultiSeriesChartDataGenerator");
			registe("MSColumn2D",   			"uap.bq.chart.generator.MultiSeriesChartDataGenerator");
			registe("Radar",   			        "uap.bq.chart.generator.RadarChartDataGenerator");
			registe("RadarArea",   			    "uap.bq.chart.generator.RadarChartDataGenerator");
			registe("RadarLine",   			    "uap.bq.chart.generator.RadarChartDataGenerator");
			registe("RadarPoint",   			"uap.bq.chart.generator.RadarChartDataGenerator");
			registe("MSBar2D",   			    "uap.bq.chart.generator.MultiSeriesChartDataGenerator");
			registe("MSArea",       			"uap.bq.chart.generator.MultiSeriesChartDataGenerator");
			registe("MSLine",       			"uap.bq.chart.generator.MultiSeriesChartDataGenerator");
			registe("Marimekko",   	 			"uap.bq.chart.generator.MultiSeriesChartDataGenerator");
			registe("Pie2D",        			"uap.bq.chart.generator.SingleSetFusionChartDataGenerator");
			registe("Waterfall2D",        		"uap.bq.chart.generator.SingleSetFusionChartDataGeneratorwithVLine");
			registe("Pareto2D",     			"uap.bq.chart.generator.SingleSetFusionChartDataGenerator");
			registe("Funnel",       			"uap.bq.chart.generator.SingleSetFusionChartDataGenerator");
			registe("Pyramid",       			"uap.bq.chart.generator.SingleSetFusionChartDataGenerator");
			registe("AngularGauge", 			"uap.bq.chart.generator.GaugeFusionWidgetsChartDataGenerator");
			registe("HLinearGauge", 			"uap.bq.chart.generator.LinearGaugeFusionWidgetsChartDataGenerator");
			registe("Scatter",      			"uap.bq.chart.generator.MultiSeriesScatterChartDataGenerator");
			registe("Bubble",               	"uap.bq.chart.generator.MultiSeriesBubbleChartDataGenerator");
			registe("MSStackedColumn2D",    	"uap.bq.chart.generator.MSStackedColumnChartDataGenerator");
			registe("MultiLevelPie",        	"uap.bq.chart.generator.MultiLevelPieChartDataGenerator");
			registe("SparkColumn",         	 	"uap.bq.chart.generator.SparkChartDataGenerator");
			registe("SparkWinLoss",         	"uap.bq.chart.generator.SparkChartDataGenerator");
			registe("SparkLine",            	"uap.bq.chart.generator.SparkChartDataGenerator");
			registe("MultiAxisLine",        	"uap.bq.chart.generator.MultiAxisLineChartDataGenerator");
			registe("VBullet",        	        "uap.bq.chart.generator.BullertChartDataGenerator");
			registe("HBullet",        	        "uap.bq.chart.generator.BullertChartDataGenerator");
			registe("Thermometer",        	    "uap.bq.chart.generator.BullertChartDataGenerator");
			registe("Cylinder",        	        "uap.bq.chart.generator.BullertChartDataGenerator");
			
			inited = true;
		}
	}
	
	/**
	 * 工厂接口
	 * @param implClass
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static DataGenerator getInstance(String code) throws ClassNotFoundException{
		init();
		
		String implClass = registedCode.get(code);
		if (implClass == null || implClass.isEmpty()){
			AppDebug.debug("No register the code in code hash map!");
			return null;
		}
		//DataGenerator generator = instanceMap.get(implClass);
		DataGenerator generator = null;

		@SuppressWarnings("rawtypes")
		Class clazz = Class.forName(implClass);
		try {
			generator = (DataGenerator) clazz.newInstance();
		} catch (InstantiationException e) {
			AppDebug.error(e);
		} catch (IllegalAccessException e) {
			AppDebug.error(e);
		}
		//instanceMap.put(implClass, generator);
	
		
		return generator;
	}
	/**
	 * 
	 * @param type (0: 没有任何附加条件; 1: 禁止动画效果； 2: 开启动画效果; 3: 只创建<chart>外观属性)
	 * @param define
	 * @param model
	 * @return
	 * @throws ChartGenerateException
	 */
	public abstract DataGenerateResult generate(int type, ChartDefine define, ChartModel model) throws ChartGenerateException;
	
	public abstract void clear ();
	
}
