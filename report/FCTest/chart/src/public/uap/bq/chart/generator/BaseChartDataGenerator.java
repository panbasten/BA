package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import org.apache.commons.lang.StringUtils;

import uap.bq.chart.ChartPropConst;
import uap.bq.chart.generator.Axis.CoordinatesAxis;
import uap.bq.chart.generator.Axis.Range;
import uap.bq.chart.generator.Axis.YAxisHelp;
import uap.bq.chart.utils.ChartNumFormatUtils;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.util.bq.chart.ToolClassDefine.StringList;
import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.PropertyGroupDefine;
import uap.vo.bq.chart.define.StyleGroupDefine;
import uap.vo.bq.chart.model.AutoDivisionPropertyGroup;
import uap.vo.bq.chart.model.ChartModel;
import uap.vo.bq.chart.model.ChartModelFactory;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.StyleGroup;
import uap.vo.bq.chart.model.UserDivisionPropertyGroup;
import uap.vo.bq.chart.model.WarningPropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.databinding.IChartDataBinding;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataset;

import com.ufida.iufo.pub.tools.AppDebug;

public class BaseChartDataGenerator extends DataGenerator {
	/* inner variable declared . */
	protected ChartModel model = null;
	protected ChartDefine chartDefine = null;
	ChartDataset[] datasets = null;
	
	protected DataGenerateResult dataGenerateResult = null;
	/* 保持分组线 */
	protected List<StringBuilder> userGroupLines = new ArrayList<StringBuilder>();
	protected List<StringBuilder> autoGroupLines = new ArrayList<StringBuilder>();
	protected StringList userDivisionPoint = new StringList();
	protected String toolTipSepChar = "";
	

	
	//
	@Override
	public DataGenerateResult generate(int type, ChartDefine define,
			ChartModel model) throws ChartGenerateException {
		/* 保存上下文信息 */
		this.model = model;
		this.chartDefine = define;
		
		/**
		 * 判断默认数据的情况
		 */
		ChartDataset[] datasets = handleDataset();
		
		this.datasets = datasets;

	
		if (datasets == null || datasetisEmpty(datasets[0]))
			return new DataGenerateResult();
		
		
	  
		
		/* 实例化数据转化结果变量 */
		this.dataGenerateResult = getDataGenerateResult(type);
		

		
		// 清除垃圾引用
		this.clear();
		return dataGenerateResult;
	}
	
	
	protected  DataGenerateResult  getDataGenerateResult(int type) throws ChartGenerateException{
		 //系列绑定属性组
		DataBindingPropertyMap bindingPropMap = this.getDataBindingPropertyMap();
		
		//数据集系列绑定列
        List<DataBindingItem> dataBindingItems = getDataBindingItemList(datasets);
        
        
		List<PropertyGroup> propertyGroups = this.model.getPropertyGroupsList();
		

		if (dataBindingItems == null || dataBindingItems.size() == 0) {
			AppDebug.debug("No DataBinding Information in chartModel !");
			return new DataGenerateResult();
		}
		
		/* 实例化数据转化结果变量 */
		DataGenerateResult result = new DataGenerateResult();
		

		/*
		 * 删除列都为汇总的绑定列
		 */
		deleteAllCellIsAllColumn(dataBindingItems, datasets[0]);

		
		
		
		//把PropertyGroup属性组放到<chart.....>节点中
		result.getChartBlock(DataGenerateResult.BQ_BLOCK_CHART)
				.append(toChartProperties(propertyGroups));
		//生成Style节点
		result.getChartBlock(DataGenerateResult.BQ_BLOCK_STYLES)
				.append(toChartStyleApply(propertyGroups));
		//生成趋势线节点
		result
				.getChartBlock(DataGenerateResult.BQ_BLOCK_TRENDLINES).append(
						toChartTrendlines(propertyGroups));
		//生成垂直分组线
		toChartCategoryGroupLine(propertyGroups);
		// 只创建外观属性
		if (type == 3) {
			// dataGenerateResult.dataXml = xml.toString();
			return result;
		}
		

		//生成数据节点 <分类...> <dataset...>
		result.getChartBlock(DataGenerateResult.BQ_BLOCK_TRENDLINES).append(
						toChartDataXml(dataBindingItems, bindingPropMap,
								datasets[0]));

		result.setCategoryLabels(userDivisionPoint);

		
		return result;
	}
	
	
	
	private ChartDataset[] handleDataset(){
		 // 判断默认数据的情况
		ChartDataset[] datasets = getDatasets();
		// 如果数据为空
		if (datasets == null || datasetisEmpty(datasets[0])) {
			AppDebug.debug("No data or no data header information in Dataset!");
			ChartModel emptyDataChartModel = getemptyDataChartModel(this.chartDefine);
			this.model = mergeModelPropertyGroups(emptyDataChartModel,this.model);
			datasets = this.model.getDatasets();
		}			
		return datasets;
	}
	
	
	/**
	 * 如果model没有数据集，便取默认数据集
	 * @return
	 */
	protected ChartDataset[] getDatasets(){
		boolean modelhasnodatasets = this.model==null || this.model.getDatasets() == null || this.model
				.getDatasets().length == 0;
		
		if(modelhasnodatasets && this.chartDefine == null)
			return null;
		
		if(modelhasnodatasets){
			ChartModel defaultModel = ChartModelFactory.createChartModel(this.chartDefine.getCode());
			this.model = getDefaultDataBingChartModel(defaultModel);
			return this.chartDefine.getDefaultSetting().getDatasets();
		}else{
			return this.model.getDatasets();
		}
	}
	
	
	/**
	 * 合并绑定信息
	 * @param defaultModel
	 * @return
	 */
	protected ChartModel getDefaultDataBingChartModel(ChartModel defaultModel){
		ChartModel copyModel = (ChartModel) this.model.clone();
		PropertyGroup[] dataBindingPropertyGroups = defaultModel.getDataBindingPropertyGroups();
		copyModel.addPropertyGroup(dataBindingPropertyGroups);
		return copyModel;
	}
	
	/**
	 * 对数据进行比例缩放
	 * @param value
	 * @return
	 */
	public String getScaleValue(String value,boolean isdy2){		
		if(ChartNumFormatUtils.isDigital(value)){
			return ChartNumFormatUtils.getFormatValueNo(model, value, isdy2);
		}else{
			return value;
		}
	}
	/**
	 * 对数据标签进行比例缩放
	 * @param value
	 * @param isdy2
	 * @return
	 */
	public String getScaleDisplayValue(String value,boolean isdy2){
		if(ChartNumFormatUtils.isDigital(value)){
			return ChartNumFormatUtils.getFormatValue(value,isdy2,model,true);
		}else{
			return value;
		}
		
	}
	
	/**
	 * majormodel合并minormodel里的属性组
	 * @param majormodel
	 * @param minormodel
	 * @return
	 */
	private ChartModel mergeModelPropertyGroups(ChartModel majormodel,
			ChartModel minormodel) {
		majormodel.addPropertyGroup(minormodel.getPropertyGroups());
		return majormodel;
	}

	/**
	 * 删除列都为汇总的列
	 */
	private void deleteAllCellIsAllColumn(
			List<DataBindingItem> dataBindingItems, ChartDataset dataset) {
		Iterator<DataBindingItem> iterator = dataBindingItems.iterator();
		while (iterator.hasNext()) {
			DataBindingItem bindingItem = iterator.next();
			if (bindingItem.getChartDataBinding() instanceof ChartCategoryAxis) {
				String columnCode = bindingItem.getChartDataBinding()
						.getColumnCode();
				boolean allCellIsAll = testAllCellIsAllColumn(columnCode,
						dataset);
				if (allCellIsAll)
					iterator.remove();
			}
		}

	}

	/**
	 * 测试列是否都为汇总
	 */
	private boolean testAllCellIsAllColumn(String columnCode,
			ChartDataset dataset) {
		if (columnCode == null || columnCode.equals("") || dataset == null)
			return false;
		for (ChartBodyRow row : dataset.getChartBody().getChartBodyRows()) {
			if (!row.getChartDataCell(columnCode).isAll())
				return false;
		}
		return true;
	}

	/**
	 * 数据集是否为空
	 * 
	 * @param datasets
	 * @return
	 */
	private boolean datasetisEmpty(ChartDataset dataset) {
		return dataset == null || dataset.getChartBody() == null
				|| dataset.getChartBody().getChartBodyRows() == null
				|| dataset.getChartBody().getChartBodyRows().length == 0
				|| dataset.getChartHeader() == null
				|| dataset.getChartHeader().getHeaderCellCount() == 0;
	}

	/**
	 * 构造一个空数据集的ChartModel对象
	 * 
	 * @param define
	 * @return
	 */
	private ChartModel getemptyDataChartModel(ChartDefine define) {
		return ChartModelFactory.createEmptyDataChartModel(define);
	}

	
	
	
	private DataBindingPropertyMap getDataBindingPropertyMap(){
		DataBindingPropertyMap result = new DataBindingPropertyMap();
		for (PropertyGroup group : this.model.getPropertyGroups()) {
			if (group instanceof DataBindingRefGroup) {
				result.put(
						((DataBindingRefGroup) group).getDatasetDefine() + "-"
								+ ((DataBindingRefGroup) group).getColumn(),
						(DataBindingRefGroup) group);
			}
		}
		
		return result;
	}

	
	
	
	private List<DataBindingItem> getDataBindingItemList(ChartDataset[] datasets){
		int position = 0;
		List<DataBindingItem> result = new LinkedList<DataBindingItem>();
		for (IChartDataBinding dbg : this.model.getDataBindings()) {
			if(datasetHasTheChartDataBinding(dbg, datasets)){
				result.add(new DataBindingItem(position++, dbg,null));
			}else{
				AppDebug.debug("No dataset has the DataBindingItem:"+ dbg);
			}
		}
		
		return result;
	}
	
	
	private boolean datasetHasTheChartDataBinding(IChartDataBinding dbg,ChartDataset[] datasets){
		for(ChartDataset dataset : datasets){
			if(dbg.getDatasetCode().equals(dataset.getCode()))
				return true;
		}
		return false;
	}

	
	protected void toCustomProperty(Property property) {
	}

	
	protected StringBuilder toChartProperties(List<PropertyGroup> propertyGroups) {
		StringBuilder chartXml = new StringBuilder("<chart ");

		chartXml.append(overLapChartNodeProperty());
		
		for (PropertyGroup group : propertyGroups) {
			if (group.getClass().getName().endsWith(".PropertyGroup")) {
				if (group.getProperties() != null) {
					chartXml.append(toAttributeString(group));
				}
			}
		}

		chartXml.append(addChartNodeProperty());
		
		chartXml.append(">");

		return chartXml;
	}

	/**
	 * 覆盖被批处理属性组里的属性值
	 * @return
	 */
	protected StringBuilder overLapChartNodeProperty(){
		return autoYAxis();
	}
	
	/**
	 * 在<Chart...>节点中加属性值
	 * @return
	 */
	protected StringBuilder addChartNodeProperty(){
		return new StringBuilder();
	}
	
	
	
	private StringBuilder autoYAxis(){
		YAxisHelp yhelp = new YAxisHelp(this.model);
		String yAxisMaxValue = yhelp.getyAxisMaxValue();
		if(yAxisMaxValue != null)
			return new StringBuilder();
		
		String yAxisMinValue = yhelp.getyAxisMinValue();
		if(yAxisMinValue != null)
			return new StringBuilder();
		
		String numDivLines = yhelp.getnumDivLines();
		if(numDivLines != null)
			return new StringBuilder();
		
		return getAutoYAxisSetting();
	}
	
	
	
	private StringBuilder getAutoYAxisSetting(){
		List<String> yseries = new LinkedList<String>();
		for(IChartDataBinding bingding : this.model.getDataBindings()){
			if(bingding instanceof ChartSeriesAxis)
				yseries.add(bingding.getColumnCode());
		};
		if(yseries.size() < 2)
			return new StringBuilder();
		Range range = Range.getRange(this.datasets[0], yseries);
		if(range == null)
			return new StringBuilder();
		CoordinatesAxis coordinatesAxi = new CoordinatesAxis();
		List<Double> coors = coordinatesAxi.generateCoordinates(range.minvalue,range.maxvalue);
		StringBuilder result = new StringBuilder();
		result.append(" numDivLines='").append(coors.size()-2).append("' ")
		      .append(" yAxisMaxValue='")
		      .append(this.getScaleValue(String.valueOf(coors.get(coors.size()-1)), false)).append("' ")
		      .append(" yAxisMinValue='")
		      .append(this.getScaleValue(String.valueOf(coors.get(0)), false)).append("' ");
		return result;
	}
	
	protected void toChartCategoryGroupLine(List<PropertyGroup> propertyGroups) {
		userGroupLines.clear();
		autoGroupLines.clear();

		for (PropertyGroup group : propertyGroups) {
			// 根据type, 放入缓存， 做二次生成
			if (group instanceof UserDivisionPropertyGroup) {
				this.userGroupLines.add(toGroupLine(group, "vLine"));
			} else if (group instanceof AutoDivisionPropertyGroup) {
				this.autoGroupLines.add(toGroupLine(group, "vLine"));
			}
		}
	}

	protected StringBuilder toChartStyleApply(List<PropertyGroup> propertyGroups) {
		StringBuilder chartXml = new StringBuilder("");
		StringBuilder definitionXml = new StringBuilder();
		StringBuilder applicationXml = new StringBuilder();

		for (PropertyGroup group : propertyGroups) {
			// 根据type, 放入缓存， 做二次生成
			if (group instanceof StyleGroup) {
				// 生成样式
				toStyleGroup((StyleGroup) group, definitionXml, applicationXml);
			}
		}

		// styledefine
		if (definitionXml.length() != 0 && applicationXml.length() != 0) {
			chartXml.append("<styles>").append("<definition>");
			chartXml.append(definitionXml).append("</definition>");
			chartXml.append("<application>").append(applicationXml);
			chartXml.append("</application>").append("</styles>");
		}

		return chartXml;
	}

	protected StringBuilder toChartTrendlines(List<PropertyGroup> propertyGroups) {
		StringBuilder preWarnings = new StringBuilder("<trendLines>");
		for (PropertyGroup group : propertyGroups) {
			// 根据type, 放入缓存， 做二次生成
			if (group instanceof WarningPropertyGroup) {
				Property startValue = group.getPropertyByRefCode("startValue");
				if (startValue != null
						&& (!StringUtils.isEmpty(startValue.getValue()))) {
					preWarnings.append(toScaleGroupLine(group));
				}
			}
		}
		preWarnings.append("</trendLines>");
		return preWarnings;
	}
	
	
	protected StringBuilder toScaleGroupLine(PropertyGroup group){
		StringBuilder objectXml = new StringBuilder();
		ChartNumFormatUtils.setGenerate(group);
		for (Property property : group.getProperties()) {
			if (!property.isGenerate())
				toCustomProperty(property);
			if (property.getValue() != null && property.isGenerate()) {
				
				objectXml
						.append(" ")
						.append(property.getCode() == null ? "" : property
								.getCode())
						.append("='");
				if(property.getCode().equals("startValue") || property.getCode().equals("endValue")){
					objectXml.append(property.getValue() == null ? "" : this.getScaleValue(property
							.getValue(), false));
					objectXml.append("' ");
				}else{
					objectXml.append(property.getValue() == null ? "" : property
								.getValue()).append("' ");
				}
				
			}
		}
		StringBuilder xml =new StringBuilder();
		xml.append("<").append("line ").append(objectXml).append("/>");
		return xml;
	}
	
	


	/**
	 * 创建分组线
	 * 
	 * @param group
	 * @param vLine2
	 */
	protected StringBuilder toGroupLine(PropertyGroup group, String tagName) {
		StringBuilder xml = new StringBuilder();
		return xml.append("<").append(tagName).append(toAttributeString(group))
				.append("/>");
	}

	/**
	 * 将group中的属性， 生成xml中的属性中，
	 * 
	 * @param group
	 * @param objectXml
	 */
	protected StringBuilder toAttributeString(PropertyGroup group) {
		StringBuilder objectXml = new StringBuilder();
		ChartNumFormatUtils.setGenerate(group);
		for (Property property : group.getProperties()) {			
			if (!property.isGenerate())
				toCustomProperty(property);
			if (property.getValue() != null && property.isGenerate()) {
				if (ChartPropConst.TOOLTIP_SEPCHAR
						.equals(property.getRefCode())) {
					toolTipSepChar = property.getValue();
				}
				objectXml
						.append(" ")
						.append(property.getCode() == null ? "" : property
								.getCode())
						.append("='");
				if(isZoomScale(property.getCode())){
					objectXml.append(property.getValue() == null ? "" : this.getScaleValue(property
							.getValue(),false) ).append("' ");
				}else if(isZoomScaleWithSYAxis(property.getCode())){
					objectXml.append(property.getValue() == null ? "" : this.getScaleValue(property
							.getValue(),true) ).append("' ");
				}else{
					objectXml.append(property.getValue() == null ? "" : property
								.getValue()).append("' ");
				}
				
			}
		}
		return objectXml;
	}
	
	
	protected boolean isZoomScale(String code){
		if(code == null)
			return false;
		return code.equals("yAxisMaxValue") || code.equals("yAxisMinValue") ||
				code.equals("lowerLimit") || code.equals("upperLimit") ||
				code.equals("maxValue") || code.equals("minValue") ||
				code.equals("PYAxisMaxValue") || code.equals("PYAxisMinValue");
	}
	
	protected boolean isZoomScaleWithSYAxis(String code){
		if(code == null)
			return false;
		return code.equals("SYAxisMaxValue") || code.equals("SYAxisMinValue");
	}
	

	// 生成style
	/**
	 * 生成style样式结构
	 * 
	 * @param group
	 * @param definitionXml
	 * @param applicationXml
	 */
	private void toStyleGroup(StyleGroup group, StringBuilder definitionXml,
			StringBuilder applicationXml) {
		if (null == definitionXml || null == applicationXml || null == group) {
			return;
		}
		applicationXml.append("<apply toObject = '")
				.append(group.getApplyObject()).append("' styles ='")
				.append(group.getCode()).append("'/>");
		definitionXml.append("<style name ='").append(group.getCode())
				.append("' ");

		// 支持 fusionchart的 font bevel 等style类型
		// ChartDefine chartDefine =
		// ChartDefineFactory.getInstance().getChartDefine(this.model.getChartDefineCode());
		// 由于生成的data.xml出现了多余的 type属性，暂时去掉此部分。modify by wangqzh 2013.7.15,
		// 这里是在处理默认数据结构中或者StyleGroup对象中的数据跟chartDefine没关系
		// TODO: 此处需要再商定
		if (chartDefine != null) {

			PropertyGroupDefine defInfo = chartDefine
					.getPropertyGroupDefine(group.getRefCode());
			if (defInfo != null) {
				definitionXml.append(" type='");
				definitionXml.append(((StyleGroupDefine) defInfo).getRawType());
				definitionXml.append("'");
			}
		}

		if (group.getProperties() != null)
			for (Property prop : group.getProperties()) {
				if (prop.isGenerate())
					definitionXml.append(" " + prop.getRefCode())
							.append(" = '").append(prop.getValue()).append("'");
			}
		definitionXml.append("/>");
	}

	/**
	 * 创建统计图fusionchart渲染数据
	 * 
	 * @param dataBindList
	 * @param dataBindingPropMap
	 * @param datasetMap
	 * @return StringBuilder，渲染数据字符串构造器对象
	 * @throws ChartGenerateException
	 */
	public StringBuilder toChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {
		StringBuilder xml = new StringBuilder();
		userDivisionPoint.clear();
		return xml;
	}

	

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		this.model = null;
		this.chartDefine = null;
		this.datasets = null;
		// dataGenerateResult = null;
		/* 保持分组线 */
		this.userGroupLines.clear();
		this.autoGroupLines.clear();
		this.userDivisionPoint.clear();
		this.toolTipSepChar = "";
	}

	

}
