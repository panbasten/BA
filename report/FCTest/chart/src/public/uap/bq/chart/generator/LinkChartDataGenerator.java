package uap.bq.chart.generator;

import java.util.List;

import uap.bq.chart.ChartClickLinkConst;
import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.bq.chart.ChartPropConst;
import uap.bq.chart.generator.link.JsonCellUtils;
import uap.bq.chart.generator.link.JsonStack;
import uap.bq.chart.utils.ChartNumFormatUtils;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.Property;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;
import uap.vo.bq.chart.model.dataset.ChartHeaderCell;

public abstract class LinkChartDataGenerator extends BaseChartDataGenerator {

	/**
	 * 数据集
	 */
	protected ChartDataset dataset;

	protected static final String linkstart = "javascript:_processLink(\\\"{";
	protected static final String linkend = "}\\\")";
	protected JsonStack<AxiscodeANDChartDataCell> linkCategoryAixsstack;
	protected JsonStack<AxiscodeANDChartDataCell> linkSeriesAxisstack;
	protected JsonStack<String> categorystack;

	// protected JsonStack<String> valuestack;

	@Override
	public void clear() {
		super.clear();
		dataset = null;
		linkCategoryAixsstack = null;
		linkSeriesAxisstack = null;
		categorystack = null;
		// valuestack = null;
	}

	public StringBuilder toChartDataXml(List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {
		this.dataset = dataset;
		init();

		return generateLinkChartDataXml(dataBindingItems, dbpMap, dataset);
	}

	protected abstract StringBuilder generateLinkChartDataXml(
			List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException;

	protected void init() {
		linkCategoryAixsstack = new JsonStack<AxiscodeANDChartDataCell>();
		linkSeriesAxisstack = new JsonStack<AxiscodeANDChartDataCell>();
		categorystack = new JsonStack<String>();
		// valuestack = new JsonStack<String>();
	}

	protected StringBuilder getlink(ValueChartType type) {// linkSeriesAxisstack
		// if(1==1)
		// return new StringBuilder();

		StringBuilder link = new StringBuilder();
		link.append(this.linkstart);
		link.append(JsonCellUtils.getJsonCell(
				ChartClickLinkConst.KEY_DATASET_CODE, dataset.getCode()));

		String jsontemp;

		switch (type) {
		case VALUE_CHART_CATEGORY:

			if (categorystack != null && !categorystack.empty()) {
				link.append(",")
						.append(JsonCellUtils.getJsonCell(
								ChartClickLinkConst.KEY_CHART_TYPE,
								ValueChartType.VALUE_CHART_CATEGORY.toString()));
				jsontemp = JsonCellUtils.getJsonCellWhileValueISJsonObject(
						ChartClickLinkConst.KEY_CHART_REALCATEGORY,
						categorystack.toJsonArray());
				if (jsontemp != null && !jsontemp.equals("")) {
					link.append(",").append(jsontemp);
				}
			} else {
				return null;
			}

			break;

		case VALUE_CHART_SET:
			link.append(",").append(
					JsonCellUtils.getJsonCell(
							ChartClickLinkConst.KEY_CHART_TYPE,
							ValueChartType.VALUE_CHART_SET.toString()));

			if (categorystack != null && !categorystack.empty()) {
				jsontemp = JsonCellUtils.getJsonCellWhileValueISJsonObject(
						ChartClickLinkConst.KEY_CHART_REALCATEGORY,
						categorystack.toJsonArray());
				if (jsontemp != null && !jsontemp.equals("")) {
					link.append(",").append(jsontemp);
				}
			}

			break;

		case VALUE_CHART_LEGEND:
			link.append(",").append(
					JsonCellUtils.getJsonCell(
							ChartClickLinkConst.KEY_CHART_TYPE,
							ValueChartType.VALUE_CHART_LEGEND.toString()));

			break;
		}

		if (linkCategoryAixsstack != null && !linkCategoryAixsstack.empty()) {
			StringBuilder linktemp2 = new StringBuilder();
			linktemp2.append("{").append(linkCategoryAixsstack).append("}");
			link.append(",").append(
					JsonCellUtils.getJsonCellWhileValueISJsonObject(
							ChartClickLinkConst.KEY_CHART_CATEGORYAXIS,
							linktemp2.toString()));
		}

		if (linkSeriesAxisstack != null && !linkSeriesAxisstack.empty()) {
			StringBuilder linktemp3 = new StringBuilder();
			linktemp3.append("{").append(linkSeriesAxisstack).append("}");
			link.append(",").append(
					JsonCellUtils.getJsonCellWhileValueISJsonObject(
							ChartClickLinkConst.KEY_CHART_SERIESAXIS,
							linktemp3.toString()));
		}

		link.append(this.linkend);
		return link;
	}

	class AxiscodeANDChartDataCell {
		/**
		 * 数据列的code
		 */
		String columnCode;
		/**
		 * 当前对应数据列code的数据元素
		 */
		ChartDataCell datacell;

		public AxiscodeANDChartDataCell(String columnCode,
				ChartDataCell datacell) {
			this.columnCode = columnCode;
			this.datacell = datacell;
		}

		public String toString() {
			if (this.columnCode == null)
				return "";
			StringBuilder result = new StringBuilder();
			ChartHeaderCell headcell = dataset.getChartHeader().getHeaderCell(
					columnCode);
			result.append(JsonCellUtils.getJsonCellWhileValueISJsonObject(
					columnCode,
					JsonCellUtils.getChartHeaderJsonObject(headcell, datacell)));
			return result.toString();
		}
	}

	/**
	 * 将group中的属性， 生成xml中的属性中，
	 * 
	 * @param group
	 * @param objectXml
	 */
	@Override
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
								.getCode()).append("='");
				// .append(getValue(property)).append("' ");
				if (isZoomScale(property.getCode())) {
					objectXml.append(
							property.getValue() == null ? "" : this
									.getScaleValue(property.getValue(), false))
							.append("' ");
				} else if(isZoomScaleWithSYAxis(property.getCode())){
					objectXml.append(property.getValue() == null ? "" : this.getScaleValue(property
							.getValue(),true) ).append("' ");
				}else {
					objectXml.append(
							property.getValue() == null ? "" : property
									.getValue()).append("' ");
				}
			}
		}
		return objectXml;
	}

	private String getValue(Property property) {
		String limits = "";
		if (property == null || property.getValue() == null) {
			return limits;
		}
		limits = property.getValue();
		if (ChartPropConst.PROPERTY_LOWERLIMIT.equals(property.getRefCode())
				|| ChartPropConst.PROPERTY_UPPERLIMIT.equals(property
						.getRefCode())
				|| ChartPropConst.PROPERTY_MAXVALUE.equals(property
						.getRefCode())
				|| ChartPropConst.PROPERTY_MINVALUE.equals(property
						.getRefCode())) {
			limits = getScaleValue(limits, false);
		}
		return limits;

	}

}
