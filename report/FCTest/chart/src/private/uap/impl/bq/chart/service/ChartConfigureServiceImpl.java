package uap.impl.bq.chart.service;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import uap.impl.bq.chart.util.BackEndServerInfoUtil;
import uap.impl.bq.chart.util.DefineUtil;
import uap.itf.bq.chart.IChartConfigureService;
import uap.util.bq.chart.ChartUtil;
import uap.vo.bq.chart.define.Icon;
import uap.vo.bq.chart.define.Icon.IconInfo;
import uap.vo.bq.chart.service.impl.ChartConfigure;
import uap.vo.bq.chart.service.impl.ChartConfigure.ChartConfigureCategories;
import uap.vo.bq.chart.service.impl.ChartConfigure.ChartConfigureDefines;
import uap.vo.bq.chart.service.impl.ChartConfigure.ChartConfigureDriver;
import uap.vo.bq.chart.service.impl.ChartConfigure.ChartConfigureDriver.ChartDriver;
import uap.vo.bq.chart.service.impl.ChartConfigure.ChartConfigureGenerators;
import uap.vo.bq.chart.service.impl.ChartConfigure.ChartConfigureParsers;
import uap.vo.bq.chart.service.impl.ChartConfigure.ChartConfigureTypes;

import com.ufida.iufo.pub.tools.AppDebug;

public class ChartConfigureServiceImpl implements IChartConfigureService {
	
	private static ChartConfigureServiceImpl instance = null;
	
	private ChartConfigure chartConfigure = null;
	
	static{
		instance = new ChartConfigureServiceImpl();
		
		instance.initialize();
	}
	
	public static  IChartConfigureService getInstance(){
		return instance;
	}

	@Override
	public String[] getChartCodes() {
		// TODO Auto-generated method stub
		//final String [] codes = {"StackedColumn2D", "StackedColumn2DLine", "MSCombi2D", "MSCombiDY2D", "MSColumn2D", "MSBar2D", "MSArea", "MSLine", "Marimekko", "Pie2D", "Waterfall2D", "Pareto2D", "Funnel", "AngularGauge", "Scatter", "Bubble", "MSStackedColumn2D", "MultiLevelPie", "SparkColumn", "SparkWinLoss", "SparkLine", "MultiAxisLine", "VBullet"};
		if (null != chartConfigure){
			return chartConfigure.getChartConfigureDefines().getChartDefineCodes();
		}
		return null;
	}

	@Override
	public String getChartType(String code) {
		// TODO Auto-generated method stub
		if (null != chartConfigure){
			return chartConfigure.getChartConfigureTypes().getChartTypeName(code);
		}
		return null;
	}

	@Override
	public String getChartGenerator(String codeOrtype) {
		// TODO Auto-generated method stub
		if (null != chartConfigure){
			return chartConfigure.getChartConfigureGenerators().getChartDriverClass(codeOrtype);
		}
		return null;
	}
	
	// ---------------------------------------------------------------//
	/**
	 * 解析统计图分类信息
	 * @param elem
	 * @return
	 */
	private ChartConfigureCategories parserChartConfigureCategory (Element elem){
		if (null == elem || elem.elements().size() == 0){
			return null;
		}
		
		ChartConfigureCategories chartConfigureCategories = new ChartConfigureCategories ();
		for (Element e : elem.elements()){
			chartConfigureCategories.addChartCategory(new ChartConfigure.InnerData(DefineUtil.getAttributeValue(e, "code"), 
					ChartUtil.FromLangRes(e, "name")));
		}
		return chartConfigureCategories;
	}
	
	/**
	 * 接地统计图类型信息
	 * @param elem
	 * @return
	 */
	private ChartConfigureTypes parserChartConfigureTypes (Element elem){
		if (null == elem || elem.elements().size() == 0){
			return null;
		}
		
		ChartConfigureTypes chartConfigureTypes = new ChartConfigureTypes ();
		for (Element e : elem.elements()){
		chartConfigureTypes.addChartType(new ChartConfigure.InnerData(DefineUtil.getAttributeValue(e, "code"), 
				ChartUtil.FromLangRes(e, "name")));
		}
		return chartConfigureTypes;
	} 
	/**
	 * 解析统计图定义概要信息
	 * @param elem
	 * @return
	 */
	private ChartConfigureDefines parserChartConfigureDefines (Element elem){
		if (elem == null || elem.elements().size() == 0){
			return null;
		}
		
		ChartConfigureDefines chartConfigureDefines = new ChartConfigureDefines();
		int index = 0;
		for (Element e : elem.elements()){
			String code = DefineUtil.getAttributeValue(e, "code");
			String type = DefineUtil.getAttributeValue(e, "type");
			String category = DefineUtil.getAttributeValue(e, "category");
			String name = ChartUtil.FromLangRes(e, "name");
			String iconStr = DefineUtil.getAttributeValue(e, "icon");
			boolean enable = DefineUtil.getAttributeValue_boolean(e, "enable");
			
			//根据iconStr实例化icon对象
			IconInfo info = new Icon.IconInfo();
			info.littleIcon = iconStr;
			Icon icon = new Icon(info);
			
			chartConfigureDefines.addChartDefineDigestEx(new ChartConfigure.ChartConfigureDefines.ChartDefineDigestEx(index++, type, code, name, category, icon,enable));
		}
		
		return chartConfigureDefines;
	}
	/**
	 * 解析数据加载器驱动
	 * @param elem
	 * @return
	 */
	private ChartConfigureDriver parserChartConfigureParsers (Element elem){
		ChartConfigureParsers driver = new ChartConfigureParsers(
				DefineUtil.getAttributeValue(elem, "code"),
				ChartUtil.FromLangRes(elem, "name"), 
				DefineUtil.getAttributeValue_boolean(elem, "enable"));
		
		for(Element e : elem.elements()){
			driver.addChartDriver(new ChartDriver (DefineUtil.getAttributeValue(e, "code"),
					ChartUtil.FromLangRes(e, "name"),
					DefineUtil.getAttributeValue(e, "type"),
					DefineUtil.getAttributeValue(e, "class")));
		}
		return driver;
	}
	
	/**
	 * 解析数据生成器驱动xml
	 * @param elem
	 * @return
	 */
	private ChartConfigureDriver parserChartConfigreGenerators (Element elem){
		ChartConfigureGenerators driver = new ChartConfigureGenerators(DefineUtil.getAttributeValue(elem, "code"),
				ChartUtil.FromLangRes(elem, "name"), 
				DefineUtil.getAttributeValue_boolean(elem, "enable"));
		
		
		for(Element e : elem.elements()){
			
			driver.addChartDriver(new ChartDriver (DefineUtil.getAttributeValue(e, "code"),
					ChartUtil.FromLangRes(e, "name"),
					DefineUtil.getAttributeValue(e, "type"),
					DefineUtil.getAttributeValue(e, "class")));
			
		}
		return driver;
		
	}
	
	
	/**
	 * 解析配置文件
	 * @param elem
	 * @return
	 */
	
	private ChartConfigure parseChartConfigure(Element elem){
		if (elem == null || elem.elements().size() == 0){
			return null;
		}
		
		ChartConfigure tmpChartConfigure = new ChartConfigure ();
		for(Element element : elem.elements()){
			String tagName = element.getName();
			if("charts".equals(tagName)){
				tmpChartConfigure.setChartConfigureDefines(this.parserChartConfigureDefines(element));
			}
			else if("drivers".equals(tagName)){
				for (Element e : element.elements()){
					if (e.getName().equals("parsers")){
						tmpChartConfigure.setChartConfigureParsers(this.parserChartConfigureParsers(e));
					}
					else if (e.getName().equals("generators")){
						tmpChartConfigure.setChartConfigureGenerators(this.parserChartConfigreGenerators(e));
					}
				}
			}
			else if("categories".equals(tagName)){
				tmpChartConfigure.setChartConfigureCategories(this.parserChartConfigureCategory(element));
			}
			else if("types".equals(tagName)){
				tmpChartConfigure.setChartConfigureTypes(this.parserChartConfigureTypes(element));
			}
		}
		return tmpChartConfigure;
	}
	
	private boolean parserChartConfigure (String url){
		File xml = new File(url);
		if(!xml.isFile()){
			return false;
		}
		SAXReader sax = new SAXReader();
		try {
			Document xmlDoc = sax.read(xml);
			
			this.chartConfigure = parseChartConfigure (xmlDoc.getRootElement());
			
			return chartConfigure != null;
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 初始化函数
	 */
	public void initialize (){
		
		String rootPath = BackEndServerInfoUtil.getServerRootPath();
//		String url = "D:/Git-New/bq_bqrt_chart/chart/web/chartDefines/chartConfigure.xml";//rootPath + "/chart/chartDefines/chartConfigure.xml";
		if (!parserChartConfigure(rootPath + "/chartDefines/chartConfigure.xml")){
			AppDebug.debug("## Read Chart Configure File Failed!");
		}
	}
	
	//--------------------------------------------//
//	public static void main (String[] args){
//		ServerInfoUtil.setLocalModel();
//		
//		String[] codes = ChartConfigureServiceImpl.getInstance().getChartCodes();
//		String classImpl = ChartConfigureServiceImpl.getInstance().getChartGenerator("MSColumn2D");
//		//Stirng[] ss = obj.getChartGenerator(codeOrtype);
//		System.out.println(codes.toString());
//	}
}
