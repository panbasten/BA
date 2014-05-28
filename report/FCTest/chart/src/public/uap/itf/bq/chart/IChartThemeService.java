package uap.itf.bq.chart;

import uap.vo.bq.chart.define.ChartTheme;
import uap.vo.bq.chart.define.ChartTheme.ChartPaletteColor;
import uap.vo.bq.chart.define.ChartTheme.ChartStyle;

public interface IChartThemeService {
	/**
	 * 根据给定的色系code代码获取色系内容
	 * @param code
	 * @return
	 */
	public ChartPaletteColor getChartPaletteColor (String code);
	
	/**
	 * 获取所有色系内容
	 * @return
	 */
	public ChartPaletteColor[] getChartPaletteColors ();
	/**
	 * 根据主题编码获取主题实例
	 * @param code
	 * @return 主题实例
	 */
	public ChartStyle getChartStyle (String code);
	
	/**
	 * 获取多个主题
	 * @param codes
	 * @return
	 */
	public ChartStyle[] getChartStyles(String[] codes);
	
	public ChartTheme getChartTheme (String code);
	public ChartTheme[] getChartThemes(String[] codes);
	
	/**
	 * 更新服务器端主题方法（从新从磁盘读取新的默认主题内容）
	 * @return
	 */
	public boolean updateChartThemes();
}
