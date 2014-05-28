package uap.bq.chart;

public class ChartClickLinkConst {
	
	/**
	 * 统计图系列（度量）
	 */
	public static final String KEY_CHART_CATEGORYAXIS = "categoryAxis";
	/**
	 * 统计图分类（维度）
	 */
	public static final String KEY_CHART_SERIESAXIS = "seriesAxis";
	/**
	 * 数据集的ID
	 */
	public static final String KEY_DATASET_CODE = "datasetcode";
	/**
	 * 维度（分类）信息
	 */
	public static final String KEY_CHART_REALCATEGORY = "categoryAxisCodes";//keyChartCategoryAxis
	/**
	 * 指标（度量）信息
	 */
	public static final String KEY_CHART_SERIESCATEGORY = "seriesCategoryAxisCodes";//keyChartSeriesAxis
	
	
	
	/**
	 * 统计图点击类型
	 */
	public static final String KEY_CHART_TYPE = "clickPointType";
	/*
	 * 点击类型的取值
	 */
	public static enum ValueChartType{
		VALUE_CHART_SET,VALUE_CHART_CATEGORY,VALUE_CHART_LEGEND;
    }
	/**
	 * 统计图值标签，比如柱形图的柱子、线图面积图的锚点、饼图的扇形
	 */
	public static final String VALUE_CHART_SET = "VALUE_CHART_SET";
	/**
	 * 统计图X轴分类标签
	 */
	public static final String VALUE_CHART_CATEGORY = "VALUE_CHART_CATEGORY";
	/**
	 * 统计图图例
	 */
	public static final String VALUE_CHART_LEGEND = "VALUE_CHART_LEGEND";
	
	
	
	/**
	 * 数据元素的code
	 */
	public static final String KEY_CHARTDATACELL_CODE = "code";
	/**
	 * 数据元素的pkValue
	 */
	public static final String KEY_CHARTDATACELL_PKVALUE = "pkValue";
	/**
	 * 数据元素的captionValue
	 */
	public static final String KEY_CHARTDATACELL_CAPTIONVALUE = "value";
	/**
	 * 数据元素的pkValue
	 */
	public static final String KEY_CHARTDATACELL_CANDRILLUP = "canDrillUp";
	/**
	 * 数据元素的captionValue
	 */
	public static final String KEY_CHARTDATACELL_CANDRILLDOWN = "canDrillDown";
	/**
	 * 是否为汇总
	 */
	public static final String KEY_CHARTDATACELL_ISALL = "isAll";
	
	
	
	/**
	 * 列信息head元素的code
	 */
	public static final String KEY_CHARTHEADACELL_CODE = "code";
	/**
	 * 列信息head元素的pkFieldExpr
	 */
	public static final String KEY_CHARTHEADACELL_PKFIELDEXPRE = "pkFieldExpr";
	/**
	 * 列信息head元素的caption
	 */
	public static final String KEY_CHARTHEADACELL_CAPTION = "caption";
	/**
	 * 列信息head元素的value
	 */
	public static final String KEY_CHARTHEADACELL_VALUE = "value";
	/**
	 * 列信息head元素的totaltype
	 */
	public static final String KEY_CHARTHEADACELL_TOTALTYPE = "totaltype";
	
	

}
