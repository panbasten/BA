package uap.bq.chart.generator;

import java.util.ArrayList;
import java.util.List;

import uap.bq.chart.ChartClickLinkConst.ValueChartType;
import uap.bq.chart.ChartPropConst;
import uap.util.bq.chart.ToolClassDefine.DataBindingPropertyMap;
import uap.vo.bq.chart.model.PropertyGroup;
import uap.vo.bq.chart.model.RangeColorPropertyGroup;
import uap.vo.bq.chart.model.databinding.ChartSeriesAxis;
import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataset;

public class BullertChartDataGenerator extends LinkChartDataGenerator {

	/**
	 * 创建统计图fusionchart渲染数据
	 * 
	 * @param dataBindList
	 * @param dataBindingPropMap
	 * @param datasetMap
	 * @return StringBuilder，渲染数据字符串构造器对象
	 * @throws ChartGenerateException
	 */
	@Override
	public StringBuilder generateLinkChartDataXml(
			List<DataBindingItem> dataBindingItems,
			DataBindingPropertyMap dbpMap, ChartDataset dataset)
			throws ChartGenerateException {
		StringBuilder xml = new StringBuilder();
		xml.append(generateColorRange(super.userGroupLines)); // 根据userGroupLines的信息生成<colorRange>....</colorRange>
		List<ChartSeriesAxis> series = new ArrayList<ChartSeriesAxis>();
		for (DataBindingItem dbi : dataBindingItems) { // 遍历数据绑定
			if (dbi.getChartDataBinding() instanceof ChartSeriesAxis)
				series.add((ChartSeriesAxis) dbi.getChartDataBinding());
		}
		ChartBodyRow cbr = dataset.getChartBody().getChartBodyRow(0);
		String value = cbr.getChartDataCell(ChartPropConst.BULLET_VALUE_CODE)
				.getCaption();
		value = getScaleValue(value, false);
		xml.append("<value");

		linkSeriesAxisstack.push(new AxiscodeANDChartDataCell(
				ChartPropConst.BULLET_VALUE_CODE, cbr
						.getChartDataCell(ChartPropConst.BULLET_VALUE_CODE)));
		StringBuilder datasetlink = getlink(ValueChartType.VALUE_CHART_SET);
		if (datasetlink != null && !datasetlink.toString().equals(""))
			xml.append(" link='").append(datasetlink).append("' ");
		linkSeriesAxisstack.pop();

		xml.append(">").append(value).append("</value>");

		if (cbr.getChartDataCell(ChartPropConst.BULLET_TARGET_CODE) != null) {
			String target = cbr.getChartDataCell(
					ChartPropConst.BULLET_TARGET_CODE).getCaption();
			target = getScaleValue(target, false);
			xml.append("<target>").append(target).append("</target>");
		}

		return xml;
	}

	// 生成<colorRange>。。。。</colorRange>
	private StringBuilder generateColorRange(List<StringBuilder> userGroupLines) {
		StringBuilder colorRange = new StringBuilder();

		colorRange.append("<colorRange>");

		for (StringBuilder sb : userGroupLines)
			colorRange.append(sb);

		colorRange.append("</colorRange>");

		return colorRange;
	}

	// 覆盖父类的方法, 此方法不在存储vline,而是存储color
	@Override
	protected void toChartCategoryGroupLine(List<PropertyGroup> propertyGroups) {
		userGroupLines.clear();
		// autoGroupLines.clear();

		for (PropertyGroup group : propertyGroups) {
			// 根据type, 放入缓存， 做二次生成
			if (group instanceof RangeColorPropertyGroup) {
				this.userGroupLines.add(toGroupLine(group, "color"));
			}
			/*
			 * else if (group instanceof AutoDivisionPropertyGroup){
			 * this.autoGroupLines.add(toGroupLine(group,"vLine")); }
			 */
		}
	}
	
	/**
	 * 覆盖被批处理属性组里的属性值
	 * @return
	 */
	protected StringBuilder overLapChartNodeProperty(){
		return new StringBuilder();
	}

}
