package uap.itf.bq.chart;

import uap.vo.bq.chart.define.ChartTheme;
import uap.vo.bq.chart.define.ChartTheme.ChartPaletteColor;
import uap.vo.bq.chart.define.ChartTheme.ChartStyle;

public interface IChartThemeService {
	/**
	 * ���ݸ�����ɫϵcode�����ȡɫϵ����
	 * @param code
	 * @return
	 */
	public ChartPaletteColor getChartPaletteColor (String code);
	
	/**
	 * ��ȡ����ɫϵ����
	 * @return
	 */
	public ChartPaletteColor[] getChartPaletteColors ();
	/**
	 * ������������ȡ����ʵ��
	 * @param code
	 * @return ����ʵ��
	 */
	public ChartStyle getChartStyle (String code);
	
	/**
	 * ��ȡ�������
	 * @param codes
	 * @return
	 */
	public ChartStyle[] getChartStyles(String[] codes);
	
	public ChartTheme getChartTheme (String code);
	public ChartTheme[] getChartThemes(String[] codes);
	
	/**
	 * ���·����������ⷽ�������´Ӵ��̶�ȡ�µ�Ĭ���������ݣ�
	 * @return
	 */
	public boolean updateChartThemes();
}
