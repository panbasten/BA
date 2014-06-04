package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;
import org.w3c.dom.Node;

import com.flywet.platform.bi.pivot.model.IStyleEnum;
import com.flywet.platform.bi.pivot.model.chart.BaseChart;
import com.flywet.platform.bi.pivot.model.chart.IChart;

public enum ChartTypeEnum implements IStyleEnum {

	// 柱形图
	COLUMN((short) 0, "column"),
	// 堆积柱形图
	COLUMNSTACKED((short) 1, "columnStacked"),
	// 100%堆积柱形图
	COLUMNSTACKEDPERCENT((short) 1, "columnStackedPercent"),
	// 重叠柱形图
	COLUMNPLACEMENT((short) 0, "columnPlacement"),
	// 条形图
	BAR((short) 2, "bar"),
	// 堆积条形图
	BARSTACKED((short) 3, "barStacked"),
	// 100%堆积条形图
	BARSTACKEDPERCENT((short) 3, "barStackedPercent"),
	// 重叠条形图
	BARPLACEMENT((short) 0, "barPlacement"),
	// 面积图
	AREA((short) 4, "area"),
	// 折线图
	LINE((short) 5, "line"),
	// 饼图
	PIE((short) 6, "pie"),
	// 多级饼图
	MULTILEVELPIE((short) 7, "multiLevelPie"),
	// 单Y轴组合图
	COMBO((short) 8, "combo"),
	// 多Y轴组合图
	MULTIAXESCOMBO((short) 9, "multiAxesCombo"),
	// 散点图
	SCATTER((short) 10, "scatter"),
	// 气泡图
	BUBBLE((short) 11, "bubble"),
	// 仪表盘
	ANGULARGAUGE((short) 12, "angularGauge"),
	// 雷达图
	RADAR((short) 13, "radar"),
	// 子弹图
	VBULLET((short) 14, "vBullet"),
	// 水平子弹图
	HBULLET((short) 15, "hBullet"),
	// 温度计
	THERMOMETER((short) 16, "thermometer"),
	// 量筒图
	CYLINDER((short) 17, "cylinder"),
	// 漏斗图
	FUNNEL((short) 18, "funnel"),
	// 金字塔图
	PYRAMID((short) 19, "pyramid"),
	// 瀑布图
	WATERFALL((short) 21, "waterfall");

	public static final String ENUM_NAME = "ChartType";

	public static final String PRIFIX_CHART_PACKAGE = "com.flywet.platform.bi.pivot.model.chart";
	public static final String SUBFIX_CHART_NAME = "Chart";

	private static Class<?> PKG = ChartTypeEnum.class;

	ChartTypeEnum(short index, String chartName) {
		this.index = index;
		this.chartName = chartName;
	}

	// 索引
	private short index;

	// 统计图名称
	private String chartName;

	public static ChartTypeEnum get(short index) {
		for (ChartTypeEnum e : ChartTypeEnum.values()) {
			if (e.index == index) {
				return e;
			}
		}
		return null;
	}

	public static ChartTypeEnum getByName(String chartName) {
		for (ChartTypeEnum e : ChartTypeEnum.values()) {
			if (e.chartName.equals(chartName)) {
				return e;
			}
		}
		return null;
	}

	public String getChartName() {
		return chartName;
	}

	public IChart instance(Node node) {
		return BaseChart.instance(node);
	}

	@Override
	public short getIndex() {
		return index;
	}

	@Override
	public String getText() {
		return BaseMessages.getString(PKG, ENUM_LONG_DESC_PREFIX + "."
				+ ENUM_NAME + "." + this.name());
	}

}
