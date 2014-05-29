package com.flywet.platform.bi.pivot.model.enums;

import org.pentaho.di.i18n.BaseMessages;

import com.flywet.platform.bi.pivot.model.IStyleEnum;

public enum ChartTypeEnum implements IStyleEnum {

	// 柱形图
	MSCOLUMN2D((short) 0, "MSColumn2D", true),
	// 堆积柱形图
	STACKEDCOLUMN2D((short) 1, "StackedColumn2D", true),
	// 条形图
	MSBAR2D((short) 2, "MSBar2D", true),
	// 堆积条形图
	STACKEDBAR2D((short) 3, "StackedBar2D", true),
	// 面积图
	MSAREA((short) 4, "MSArea", true),
	// 折线图
	MSLINE((short) 5, "MSLine", true),
	// 饼图
	PIE2D((short) 6, "Pie2D", false),
	// 多级饼图
	MULTILEVELPIE((short) 7, "MultiLevelPie", true),
	// 单Y轴组合图
	MSCOMBI2D((short) 8, "MSCombi2D", true),
	// 双Y轴组合图
	MSCOMBIDY2D((short) 9, "MSCombiDY2D", true),
	// 散点图
	SCATTER((short) 10, "Scatter", true),
	// 气泡图
	BUBBLE((short) 11, "Bubble", true),
	// 仪表盘
	ANGULARGAUGE((short) 12, "AngularGauge", true),
	// 雷达图
	RADAR((short) 13, "Radar", true),
	// 子弹图
	VBULLET((short) 14, "VBullet", true),
	// 水平子弹图
	HBULLET((short) 15, "HBullet", true),
	// 温度计
	THERMOMETER((short) 16, "Thermometer", true),
	// 量筒图
	CYLINDER((short) 17, "Cylinder", true),
	// 漏斗图
	FUNNEL((short) 18, "Funnel", true),
	// 金字塔图
	PYRAMID((short) 19, "Pyramid", true),
	// 刻度板
	HLINEARGAUGE((short) 20, "HLinearGauge", true),
	// 瀑布图
	WATERFALL2D((short) 21, "Waterfall2D", true),
	// 迷你柱形图
	SPARKCOLUM((short) 22, "SparkColumn", true),
	// 迷你盈亏图
	SPARKWINLOSS((short) 23, "SparkWinLoss", true),
	// 迷你柱形图
	SPARKLINE((short) 24, "SparkLine", true);

	public static final String ENUM_NAME = "ChartType";

	private static Class<?> PKG = ChartTypeEnum.class;

	ChartTypeEnum(short index, String chartName, boolean isMultiSeries) {
		this.index = index;
		this.chartName = chartName;
	}

	// 索引
	private short index;

	// 统计图名称
	private String chartName;

	// 是否是多系列
	private boolean isMultiSeries;

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

	public boolean isMultiSeries() {
		return isMultiSeries;
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
