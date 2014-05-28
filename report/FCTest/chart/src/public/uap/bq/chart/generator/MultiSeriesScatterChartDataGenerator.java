package uap.bq.chart.generator;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.bq.chart.generator.Axis.CoordinatesAxis;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.CategoriesPropertyGroup;
import uap.vo.bq.chart.model.DataBindingRefGroup;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.UserDivisionPropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartCategoryAxis;
import uap.vo.bq.chart.model.databinding.ChartXYSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;
import uap.vo.bq.chart.model.dataset.ChartHeaderCell;

import com.ufida.iufo.pub.tools.AppDebug;

public class MultiSeriesScatterChartDataGenerator extends
		LinkChartDataGenerator {

	protected HashMap<String, String> labelmapvalue;
	protected String xType = "";
	protected CategoriesPropertyGroup catePropertyGroup;
	protected boolean showVerticalLine = true;
	protected boolean bCategoryNameInToolTip = true;
	protected List<ChartXYSeriesAxis> xySeries = null;
	protected ChartCategoryAxis crossAxis = null;
	protected Set<String> xcolumnCodes = null;

	@Override
	public void clear() {
		super.clear();
		labelmapvalue = null;
		xType = "";
		catePropertyGroup = null;
		showVerticalLine = true;
		bCategoryNameInToolTip = true;
		crossAxis = null;
		xcolumnCodes = null;
		xySeries = null;
	}

	@Override
	public StringBuilder generateLinkChartDataXml(
			List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {

		StringBuilder xml = new StringBuilder();

		// 加上垂直分组线
		xml.append(tovTrendLines());

		// 分解系列绑定
		classifydataBindingItems(dataBindingItems);

		// 所有x轴系列的 类型是否一致
		boolean isConsistency = xSeriesTypeisConsistency(dataset);
		if (!isConsistency)
			return new StringBuilder();

		// 生成<<categories>.....</categories>
		xml.append(generatecategories(dataset));

		xml.append(generatedatasets(xySeries, crossAxis, dbpMap, dataset));

		return xml;

	}

	/**
	 * 生成<categories>.....</categories>
	 * 
	 * @param dataset
	 * @return
	 * @throws ChartGenerateException
	 */
	private StringBuilder generatecategories(ChartDataset dataset)
			throws ChartGenerateException {
		StringBuilder result = new StringBuilder();
		result.append("<categories ")
				.append(toDataBindingRefGroupAttributeString(catePropertyGroup))
				.append(" >");

		if (xType.equals(ChartHeaderCell.NNMBER_TYPE))
			result.append(generatecategoriesWithNumberType(xcolumnCodes,
					dataset));
		else if (xType.equals(ChartHeaderCell.STRING_TYPE))
			result.append(generatecategoriesWithStringType(xySeries.get(0)
					.getxColumnCode(), dataset));

		result.append("</categories>");
		return result;
	}

	/**
	 * 所有x轴系列的 类型是否一致
	 * 
	 * @param dataset
	 * @return
	 */
	private boolean xSeriesTypeisConsistency(ChartDataset dataset) {
		String xcolumnCode = xySeries.get(0).getxColumnCode();
		xType = dataset.getChartHeader().getHeaderCell(xcolumnCode).getType();

		if (!xType.equals(ChartHeaderCell.NNMBER_TYPE)
				&& !xType.equals(ChartHeaderCell.STRING_TYPE))
			return false;
		for (ChartXYSeriesAxis cxysa : xySeries) {
			String xxxcode = cxysa.getxColumnCode();
			String xxxType = dataset.getChartHeader().getHeaderCell(xxxcode)
					.getType();
			if (xType.equals(ChartHeaderCell.NNMBER_TYPE))
				if (!xxxType.equals(ChartHeaderCell.NNMBER_TYPE)) {
					AppDebug.debug("Error about " + xxxcode
							+ "xcolumn :   Type != Number");
					return false;
				} else if (xType.equals(ChartHeaderCell.STRING_TYPE))
					if (!xxxType.equals(ChartHeaderCell.STRING_TYPE)
							|| !xcolumnCode.equals(xxxcode)) {
						AppDebug.debug("Error about  " + xxxcode
								+ "xcolumn :  Type != String  OR Code != "
								+ xcolumnCode);
						return false;
					}
		}
		return true;
	}

	/**
	 * 分解系列绑定
	 * 
	 * @param dataBindingItems
	 */
	private void classifydataBindingItems(List<DataBindingItem> dataBindingItems) {
		xySeries = new ArrayList<ChartXYSeriesAxis>();
		xcolumnCodes = new TreeSet<String>();
		for (DataBindingItem dbi : dataBindingItems) {
			if (dbi.getChartDataBinding() instanceof ChartXYSeriesAxis) {
				ChartXYSeriesAxis csa = (ChartXYSeriesAxis) dbi
						.getChartDataBinding();
				xySeries.add(csa);
				xcolumnCodes.add(csa.getxColumnCode());
			} else if (dbi.getChartDataBinding() instanceof ChartCategoryAxis)
				crossAxis = (ChartCategoryAxis) dbi.getChartDataBinding();
		}
	}

	/**
	 * 没有交叉的情形，获取<dataset>....</dataset>
	 * 
	 * @param dbpMap
	 * @param dataset
	 * @return
	 */
	private StringBuilder getdatasetxmlUnCross(DataBindingPropertyMap dbpMap,
			ChartDataset dataset) {
		StringBuilder result = new StringBuilder();
		for (ChartXYSeriesAxis cxysa : xySeries) {
			// boolean hasOperate = cxysa.isHasOperate();
			result.append("<dataset seriesName='")
					.append(getdatasetnaem(cxysa, dataset)).append("' ");
			result.append(getDataBindingProperetyGroupInfomation(dbpMap,
					dataset, cxysa));
			if (cxysa.isHasOperate()) {
				linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cxysa
						.getyColumnCode(), null));
				StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_LEGEND);
				result.append(" link='").append(datasetlink).append("' ");
				linkSeriesAxisstack.pop();
			}
			result.append(" >");
			result.append(generateAdataset(cxysa,
					Arrays.asList(dataset.getChartBody().getChartBodyRows())));
			result.append("</dataset>");
		}
		return result;
	}

	/**
	 * 获取系列绑定属性的信息
	 * 
	 * @param dbpMap
	 * @param dataset
	 * @param cxysa
	 * @return
	 */
	private StringBuilder getDataBindingProperetyGroupInfomation(
			DataBindingPropertyMap dbpMap, ChartDataset dataset,
			ChartXYSeriesAxis cxysa) {
		StringBuilder result = new StringBuilder();
		DataBindingRefGroup dbrg = dbpMap.get(dataset.getCode() + "-"
				+ DataBindingRefGroup.COMMONCOLUMNCODE); // 根据数据集和列的code获得DataBindingRefGroup
		if (dbrg == null)
			dbrg = dbpMap.get(dataset.getCode() + "-" + cxysa.getCode()); // 根据数据集和列的code获得DataBindingRefGroup
		if (dbrg != null)
			result.append(toDataBindingRefGroupAttributeString(dbrg)); // 给<dataset
																		// ....>加属性
		return result;
	}

	/**
	 * 有交叉的情形，获取<dataset>....</dataset>
	 * 
	 * @param dbpMap
	 * @param dataset
	 * @return
	 */
	private StringBuilder getdatasetxmlCross(DataBindingPropertyMap dbpMap,
			ChartDataset dataset) {
		StringBuilder result = new StringBuilder();
		String colcode = crossAxis.getColumnCode();

		HashMap<String, List<ChartBodyRow>> dataTree = initialiseDataTree(dataset);

		for (String key : dataTree.keySet()) {
			for (ChartXYSeriesAxis cxysa : xySeries) {
				// boolean hasOperate = cxysa.isHasOperate();
				result.append("<dataset seriesName='");
				result.append(key + ": ")
						.append(getdatasetnaem(cxysa, dataset)).append("' ");
				result.append(getDataBindingProperetyGroupInfomation(dbpMap,
						dataset, cxysa));
				if (cxysa.isHasOperate()) {
					linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
							colcode, getOneChartDataCell(colcode,
									dataTree.get(key))));
					linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(cxysa
							.getxColumnCode(), null));
					StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_LEGEND);
					if (datasetlink != null && !datasetlink.toString().equals(""))
						result.append(" link='").append(datasetlink)
								.append("' ");
					linkSeriesAxisstack.pop();
					linkCategoryAixsstack.pop();
				}
				result.append(" >");
				result.append(generateAdataset(cxysa, dataTree.get(key)));
				result.append("</dataset>");
			}
		}
		return result;
	}

	/**
	 * 任意获取非空的一行
	 * 
	 * @param colcode
	 * @param rows
	 * @return
	 */
	protected ChartDataCell getOneChartDataCell(String colcode,
			List<ChartBodyRow> rows) {
		for (int i = 0; i < rows.size(); i++) {
			ChartDataCell cell = rows.get(i).getChartDataCell(colcode);
			if (cell != null)
				return cell;
		}
		return null;
	}

	/**
	 * 构造一个类似树结构的Map数据结构，叶子节点是一个由ChartBodyRow组成的List
	 * 
	 * @param dataset
	 * @return
	 */
	private HashMap<String, List<ChartBodyRow>> initialiseDataTree(
			ChartDataset dataset) {
		HashMap<String, List<ChartBodyRow>> dataTree = new HashMap<String, List<ChartBodyRow>>();
		for (ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()) {
			String categoryvalue = cbr.getChartDataCell(
					crossAxis.getColumnCode()).getCaption();
			if (!dataTree.containsKey(categoryvalue))
				dataTree.put(categoryvalue, new ArrayList<ChartBodyRow>());
			dataTree.get(categoryvalue).add(cbr);
		}
		return dataTree;
	}

	protected StringBuilder generatedatasets(List<ChartXYSeriesAxis> xySeries,
			ChartCategoryAxis cca, DataBindingPropertyMap dbpMap,
			ChartDataset dataset) {
		StringBuilder datasetsxml = new StringBuilder();
		// 没有交叉的情形，获取<dataset>....</dataset>
		if (cca == null || cca.getColumnCode().isEmpty()) {
			datasetsxml = getdatasetxmlUnCross(dbpMap, dataset);
			return datasetsxml;
		}

		// 有交叉的情形，获取<dataset>....</dataset>
		datasetsxml = getdatasetxmlCross(dbpMap, dataset);

		return datasetsxml;
	}

	/**
	 * 获取系列名称
	 * 
	 * @param cxysa
	 * @param dataset
	 * @return
	 */
	protected String getdatasetnaem(ChartXYSeriesAxis cxysa,
			ChartDataset dataset) {
		String xcolumnCode = cxysa.getxColumnCode();
		String ycolumnCode = cxysa.getyColumnCode();
		String xcolumnName = dataset.getChartHeader()
				.getHeaderCell(xcolumnCode).getCaption();
		String ycolumnName = dataset.getChartHeader()
				.getHeaderCell(ycolumnCode).getCaption();
		return xcolumnName + "_" + ycolumnName;
	}

	/**
	 * 生成一个<dataset>....</dataset>
	 * 
	 * @param cxysa
	 * @param chartBodyRows
	 * @return
	 */
	protected StringBuilder generateAdataset(ChartXYSeriesAxis cxysa,
			List<ChartBodyRow> chartBodyRows) {
		StringBuilder datasetxml = new StringBuilder();
		String xcolumnCode = cxysa.getxColumnCode();
		String ycolumnCode = cxysa.getyColumnCode();
		categorystack.push(xcolumnCode);
		//boolean hasOperate = cxysa.isHasOperate();
		for (ChartBodyRow cbr : chartBodyRows) {
			String xvalue = cbr.getChartDataCell(xcolumnCode).getCaption();
			String yvalue = cbr.getChartDataCell(ycolumnCode).getCaption();

			if (xvalue == null || xvalue.equals("") || yvalue == null
					|| yvalue.equals(""))
				continue;
			yvalue = getScaleValue(yvalue,false);
			if (xType.equals("Number"))
				datasetxml.append("<set x='").append(xvalue).append("' y='")
						.append(yvalue).append("' ");
			// .append(bCategoryNameInToolTip?"":"toolText='"+yvalue+"' ").append("/>");
			else if (xType.equals("String"))
				datasetxml.append("<set x='").append(labelmapvalue.get(xvalue))
						.append("' y='").append(yvalue).append("' ");
			if (cxysa.isHasOperate()) {
				linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
						xcolumnCode, cbr.getChartDataCell(xcolumnCode)));
				linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(
						ycolumnCode, cbr.getChartDataCell(ycolumnCode)));
				if (crossAxis != null && !crossAxis.getColumnCode().isEmpty())
					linkCategoryAixsstack.push(new AxiscodeANDChartDataCell(
							crossAxis.getColumnCode(),
							cbr.getChartDataCell(crossAxis.getColumnCode())));
				StringBuilder link = getlink(ValueChartType.VALUE_CHART_SET);
				if (link != null && !link.toString().equals(""))
					datasetxml.append(" link='").append(link).append("' ");
				linkCategoryAixsstack.pop();
				linkSeriesAxisstack.pop();
				if (crossAxis != null && !crossAxis.getColumnCode().isEmpty())
					linkCategoryAixsstack.pop();
			}
			datasetxml.append(
					bCategoryNameInToolTip ? "" : "toolText='" + yvalue + "' ")
					.append("/>");
		}
		categorystack.pop();
		return datasetxml;

	}

	/**
	 * x轴系列为数值型情形，生成<category ../>.... <category ../>
	 * 
	 * @param xcolumnCodes
	 * @param dataset
	 * @return
	 * @throws ChartGenerateException
	 */
	protected StringBuilder generatecategoriesWithNumberType(
			Set<String> xcolumnCodes, ChartDataset dataset)
			throws ChartGenerateException {
		StringBuilder categories = new StringBuilder();
		// 获取数据集中x轴系列的最大值、最小值
		Range oldrange = getRange(dataset);
		// 与xAxisMaxValue、xAxisMinValue两个属性值对比，从而获得最终的最大值、最小值
		Range newrange = getMaxvalueAndMinvalue(oldrange);

		// 生成<category ../>.... <category ../>
		categories.append(getCategoriesWithNumberType(newrange));

		return categories;
	}

	/**
	 * 生成<category ../>.... <category ../>
	 * 
	 * @param newrange
	 * @return
	 */
	private StringBuilder getCategoriesWithNumberType(Range newrange) {
		CoordinatesAxis coors = new CoordinatesAxis();
		/* 生成坐标 */
		List<Double> coordinates = coors.generateCoordinates(newrange.minvalue,
				newrange.maxvalue);
		if (coordinates == null) {
			AppDebug.debug(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes()
					.getStrByID("0502004_0", "00502004-0078"));
			return new StringBuilder();
		}
		StringBuilder result = new StringBuilder();
		String coorLabelTemp;
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(coors.getNumberOfDecimalPlace());
		nf.setMinimumFractionDigits(coors.getNumberOfDecimalPlace());
		for (Double coor : coordinates) {
			// double coorX = coor
			coorLabelTemp = nf.format(coor);
			result.append("<category label='").append(coorLabelTemp)
					.append("' x='").append(coor)
					.append("' showVerticalLine='")
					.append(showVerticalLine ? 1 : 0).append("' />");
		}
		return result;
	}

	/**
	 * 获取数据集中x轴系列的最大值、最小值
	 * 
	 * @param dataset
	 * @return
	 */
	private Range getRange(ChartDataset dataset) {
		double minvalue = Double.POSITIVE_INFINITY;
		double maxvalue = Double.NEGATIVE_INFINITY;
		String xvaluetemp;
		double temp;
		for (ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()) {
			for (String xcolumnCode : xcolumnCodes) {
				ChartDataCell datacell = cbr.getChartDataCell(xcolumnCode);
				if (datacell == null) {
					AppDebug.debug("One datacell is null!");
					continue;
				}

				xvaluetemp = datacell.getCaption();
				if (xvaluetemp == null || xvaluetemp.equals(""))
					continue;

				temp = Double.parseDouble(xvaluetemp);
				minvalue = minvalue > temp ? temp : minvalue;
				maxvalue = maxvalue > temp ? maxvalue : temp;
			}
		}
		return new Range(maxvalue, minvalue);
	}

	/**
	 * 与xAxisMaxValue、xAxisMinValue两个属性值对比，从而获得最终的最大值、最小值
	 * 
	 * @param oldrange
	 * @return
	 */
	protected Range getMaxvalueAndMinvalue(Range oldrange) {
		PropertyGroup[] groups = this.model.getPropertyGroups();
		String maxStr = null;
		String minStr = null;
		double min = oldrange.minvalue;
		double max = oldrange.maxvalue;
		for (PropertyGroup propertgroup : groups) {
			if (propertgroup.getRefCode().equals("FC_TITLES_AND_AXIS_NAMES")) {
				Property maxporpert = propertgroup
						.getPropertyByRefCode("xAxisMaxValue");
				maxStr = maxporpert == null ? null : maxporpert.getValue();
				Property minporpert = propertgroup
						.getPropertyByRefCode("xAxisMinValue");
				minStr = minporpert == null ? null : minporpert.getValue();
			}
		}
		if (maxStr != null && isNumber(maxStr)) {
			double temp = Double.valueOf(maxStr);
			if (oldrange.maxvalue < temp)
				max = temp;
		}
		if (minStr != null && isNumber(minStr)) {
			double temp = Double.valueOf(minStr);
			if (temp < oldrange.minvalue)
				min = temp;
		}
		// double[] result = new double[2];
		// result[0]=min;
		// result[1]=max;
		return new Range(max, min);
	}

	protected boolean isNumber(String value) {
		return !value.equals("")
				&& value.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * x轴为字符串型，生成<category ../>.... <category ../>
	 * 
	 * @param xcolumnCode
	 * @param dataset
	 * @return
	 */
	protected StringBuilder generatecategoriesWithStringType(
			String xcolumnCode, ChartDataset dataset) {
		StringBuilder categories = new StringBuilder();
		/*
		 * categories.append("<categories ")
		 * .append(toDataBindingRefGroupAttributeString(catePropertyGroup) )
		 * .append(" >");
		 */
		String xvaluetemp;
		int i = 0;
		HashMap<String, String> labels = new HashMap<String, String>();
		for (ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()) {
			xvaluetemp = cbr.getChartDataCell(xcolumnCode).getCaption();
			if (!labels.containsKey(xvaluetemp)) {
				i++;
				labels.put(xvaluetemp, i + "");
				categories.append("<category label='").append(xvaluetemp)
						.append("' x='").append(i)
						.append("' showVerticalLine='")
						.append(showVerticalLine ? 1 : 0).append("' />");
			}
		}
		labelmapvalue = labels;
		// categories.append("</categories>");
		return categories;
	}

	/**
	 * 将group中的属性， 生成xml中的属性中，
	 * 
	 * @param group
	 * @param objectXml
	 */
	protected StringBuilder toDataBindingRefGroupAttributeString(
			PropertyGroup group) {
		if (group == null)
			return new StringBuilder();
		StringBuilder objectXml = new StringBuilder();
		for (Property property : group.getProperties()) {
			if (!property.isGenerate())
				toCustomProperty(property);
			else if (property.getValue() != null && property.isGenerate()) {
				objectXml
						.append(" ")
						.append(property.getCode() == null ? "" : property
								.getCode())
						.append("='")
						.append(property.getValue() == null ? "" : property
								.getValue()).append("' ");
			}

		}
		return objectXml;
	}

	// 生成垂直分组线
	@Override
	protected void toChartCategoryGroupLine(List<PropertyGroup> propertyGroups) {
		userGroupLines.clear();
		// autoGroupLines.clear();

		for (PropertyGroup group : propertyGroups) {
			// 根据type, 放入缓存， 做二次生成
			if (group instanceof UserDivisionPropertyGroup) {
				Property startValue = group.getPropertyByRefCode("startValue");
				Property endValue = group.getPropertyByRefCode("endValue");
				if (startValue != null && startValue.getValue() != null
						&& (!startValue.getValue().equals(""))
						|| endValue != null && endValue.getValue() != null
						&& (!endValue.getValue().equals("")))
					userGroupLines.add(toGroupLine(group, "line"));
			}

		}
	}

	// 合并各垂直分组线
	protected StringBuilder tovTrendLines() {
		if (userGroupLines == null || userGroupLines.size() == 0)
			return new StringBuilder();
		StringBuilder vTrendLines = new StringBuilder();

		vTrendLines.append("<vTrendLines>");
		for (StringBuilder vline : userGroupLines)
			vTrendLines.append(vline);
		vTrendLines.append("</vTrendLines>");

		return vTrendLines;
	}

	@Override
	protected  DataGenerateResult  getDataGenerateResult(int type) throws ChartGenerateException{
		catePropertyGroup = getCategoriesPropertyGroup();
		return super.getDataGenerateResult(type);
	}
	
	
    private CategoriesPropertyGroup getCategoriesPropertyGroup(){
    	for (PropertyGroup group : this.model.getPropertyGroupsList()) {
    		if (group instanceof CategoriesPropertyGroup) {
				return  (CategoriesPropertyGroup) group;
			} 
    	}
    	return null;
    }
    
    
    
	@Override
	protected void toCustomProperty(Property property) {
		if (property.getRefCode().equals("showVerticalLine")) {
			// 处理用户自定义属性
//			boolean flag = property.getValue() != null
//					&& (property.getValue().equals("false") || property
//							.getValue().equals("0")) ? false : true;
			showVerticalLine = property.getValue() != null
					&& (property.getValue().equals("false") || property
							.getValue().equals("0")) ? false : true;
		} else if (property.getCode().equals("categoryNameInToolTip")
				&& chartDefine.isCustomProperty(property.getCode())) {
			// 处理用户自定义属性
			bCategoryNameInToolTip = property.getValue() != null
					&& (property.getValue().equals("false") || property
							.getValue().equals("0")) ? false : true;
		}
	}

	class Range {
		Range(double maxvalue, double minvalue) {
			this.maxvalue = maxvalue;
			this.minvalue = minvalue;
		}

		final double maxvalue;
		final double minvalue;
	}

}