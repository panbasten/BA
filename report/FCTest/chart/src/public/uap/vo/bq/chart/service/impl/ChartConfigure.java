package uap.vo.bq.chart.service.impl;

import org.apache.commons.collections.map.LinkedMap;

import uap.vo.bq.chart.define.ChartDefineDigest;
import uap.vo.bq.chart.define.Icon;

/***
 * 
 * @author wangqzh
 *
 */
public class ChartConfigure {
	public static class InnerData{
		private String code;
		private String name;
		
		public InnerData (String code, String name){
			// TODO: 
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		} 
		
	}
	/**
	 * 统计图配置文件分类信息
	 * @author wangqzh
	 *
	 */
	public static class ChartConfigureCategories{
		private LinkedMap InnerDatas = new LinkedMap();
		
		public void addChartCategory (InnerData data){
			InnerDatas.put(data.getCode(), data);
		}
		
		public InnerData getChartCategory (String code){
			return (InnerData) InnerDatas.get(code);
		}
		
		public InnerData getChartCategory (int index){
			return (InnerData) InnerDatas.get(index);
		}
		
		public int size (){
			return InnerDatas.size();
		}
		
		public void cleanup(){
			InnerDatas.clear();
		}
	}
	/**
	 * 统计图配置文件类型信息
	 * @author wangqzh
	 *
	 */
	public static class ChartConfigureTypes{
		private LinkedMap InnerDatas = new LinkedMap();
		
		public void addChartType(InnerData data){
			InnerDatas.put(data.getCode(),data);
		}
		public InnerData getChartType (String code){
			return (InnerData) InnerDatas.get(code);
		}
		
		public InnerData getChartType (int index){
			return (InnerData) InnerDatas.get(index);
		}
		
		public int size(){
			return InnerDatas.size();
		}
		
		public String getChartTypeName(String code){
			InnerData obj = getChartType (code);
			if (null != obj){
				return obj.getName();
			}
			return null;
		}
		
		public void cleanup(){
			InnerDatas.clear();
		}
	}

	/**
	 * 统计图图表概要信息类
	 * @author wangqzh
	 *
	 */
	public static class ChartConfigureDefines{
		
		public static class ChartDefineDigestEx extends ChartDefineDigest{
			private static final long serialVersionUID = 1L;
			
			
			private String path;
			private boolean enable;
			private String description;

			public ChartDefineDigestEx(int index, String type, String code,
					String name, String categroy, Icon icon,boolean enable) {
				super(index, type, code, name, categroy, icon);
				this.enable = enable;
			}
			
			public String getPath(){
				return this.path;
			}
			public void setPath(String path){
				this.path = path;
			}
			public boolean isEnable(){
				return this.enable;
			}
			public void setEnable(boolean enable){
				this.enable = enable;
			}
			public String getDescription(){
				return this.description;
			}
			public void setDescription(String description){
				this.description = description;
			}
		}
		
		private LinkedMap ChartDefineDigestExs = new LinkedMap();
		
		public void addChartDefineDigestEx (ChartDefineDigestEx obj){
			ChartDefineDigestExs.put(obj.getCode(), obj);
		}
		public ChartDefineDigestEx getChartDefine (String code){
			return (ChartDefineDigestEx) ChartDefineDigestExs.get(code);
		}
		public ChartDefineDigestEx getChartDefine (int index){
			return (ChartDefineDigestEx) ChartDefineDigestExs.get(index);
		}
		public int size (){
			return ChartDefineDigestExs.size();
		}
		
		public String[] getChartDefineCodes(){
			return (String[]) ChartDefineDigestExs.keySet().toArray(new String[size ()]);
		}
		
		public void cleanup(){
			ChartDefineDigestExs.clear();
		}
		
	}

	/**
	 * 驱动描述类
	 * @author wangqzh
	 *
	 */
	public static class ChartConfigureDriver{
		public static class ChartDriver{
			private String code;
			private String name;
			private String type;
			private String classImpl;
			
			public ChartDriver (String code, String name, String type, String classImpl){
				// TODO: 
				this.code = code;
				this.name = name;
				this.type = type;
				this.classImpl = classImpl;
			}

			public String getCode() {
				return code;
			}

			public void setCode(String code) {
				this.code = code;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getType() {
				return type;
			}

			public void setType(String type) {
				this.type = type;
			}

			public String getClassImpl() {
				return classImpl;
			}

			public void setClassImpl(String classImpl) {
				this.classImpl = classImpl;
			}
			
		}
		
		private InnerData innerData;
		private boolean enable = true;
		private LinkedMap XmlParserDrivers = new LinkedMap();
		
		public ChartConfigureDriver(String code, String name,boolean enable){
			this.innerData = new InnerData(code,name);
			this.enable = enable;
		}
		
		public InnerData getInnerData(){
			return this.innerData;
		}
		public void setInnerData(InnerData data){
			this.innerData = data;
		}
		public boolean isEnable(){
			return this.enable;
		}
		public void setEnable(boolean enable){
			this.enable = enable;
		}
		public ChartDriver[] getChartDriverList(){
			return (ChartDriver[]) this.XmlParserDrivers.asList().toArray(new ChartDriver[this.XmlParserDrivers.size()]);
		}
		
		public void addChartDriver (ChartDriver driver){
			XmlParserDrivers.put(driver.getCode(), driver);
		}
		public ChartDriver getChartDriver(String code){
			return (ChartDriver) XmlParserDrivers.get(code);
		}
		
		public String getChartDriverClass(String codeOrtype){
			ChartDriver obj = getChartDriver (codeOrtype);
			if (null != obj){
				return obj.getClassImpl();
			}
			return null;
		}
		
		public void cleanup(){
			XmlParserDrivers.clear();
		}
		
	}
	/**
	 * 统计图配置文件xml数据加载驱动
	 * @author wangqzh
	 *
	 */
	public static class ChartConfigureParsers extends ChartConfigureDriver{
		public ChartConfigureParsers(String code, String name, boolean enable){
			super (code,name,enable);
		}
	}
	/**
	 * 统计图配置文件xml数据展现驱动
	 * @author wangqzh
	 *
	 */
	public static class ChartConfigureGenerators  extends ChartConfigureDriver{
		public ChartConfigureGenerators(String code, String name, boolean enable){
			super (code,name,enable);
		}
		
	}
	
	private ChartConfigureDefines chartConfigureDefines;
	private ChartConfigureCategories chartConfigureCategories;
	private ChartConfigureTypes chartConfigureTypes;
	private ChartConfigureDriver chartConfigureParsers;
	private ChartConfigureDriver chartConfigureGenerators;
	
	// TODO: 需要完善存取方法
	public ChartConfigureCategories getChartConfigureCategories() {
		return chartConfigureCategories;
	}
	public void setChartConfigureCategories(
			ChartConfigureCategories chartConfigureCategories) {
		this.chartConfigureCategories = chartConfigureCategories;
	}
	public ChartConfigureTypes getChartConfigureTypes() {
		return chartConfigureTypes;
	}
	public void setChartConfigureTypes(ChartConfigureTypes chartConfigureTypes) {
		this.chartConfigureTypes = chartConfigureTypes;
	}
	public ChartConfigureParsers getChartConfigureParsers() {
		return (ChartConfigureParsers) chartConfigureParsers;
	}
	public void setChartConfigureParsers(ChartConfigureDriver chartConfigureParsers) {
		this.chartConfigureParsers = chartConfigureParsers;
	}
	public ChartConfigureGenerators getChartConfigureGenerators() {
		return (ChartConfigureGenerators) chartConfigureGenerators;
	}
	public void setChartConfigureGenerators(
			ChartConfigureDriver chartConfigureGenerators) {
		this.chartConfigureGenerators = chartConfigureGenerators;
	}	

	public ChartConfigureDefines getChartConfigureDefines() {
		return chartConfigureDefines;
	}
	public void setChartConfigureDefines(ChartConfigureDefines chartConfigureDefines) {
		this.chartConfigureDefines = chartConfigureDefines;
	}
	public void cleanup(){
		chartConfigureCategories.cleanup();
		chartConfigureTypes.cleanup();
		chartConfigureParsers.cleanup();
		chartConfigureGenerators.cleanup();
	}
}
