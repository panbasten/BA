package uap.itf.bq.chart;

import uap.vo.bq.chart.define.ChartDefine;
import uap.vo.bq.chart.define.ChartDefineDigest;

/**
 * ͳ��ͼ������� �÷���ֻ��cache ʹ��
 * @author zhanglld
 *
 */
public interface IChartDefineService {
	/**
	 * ����ͳ��ͼ���������ͳ��ͼ����
	 * 
	 * @param chartDefineCode ͳ��ͼ�������
	 * @return ͳ��ͼ����
	 */
	public ChartDefine getChartDefine(String chartDefineCode);
	
	/**
	 * ���ݶ��ͳ��ͼ��������ö��ͳ��ͼ�������
	 * 
	 * @param chartDefineCodes
	 * @return ͳ��ͼ��������
	 */
	public ChartDefine[] getChartDefines(String[] chartDefineCodes);
	
	/**
	 * ��õ�ǰ���е�ͳ��ͼժҪ
	 * 
	 * @return ͳ��ͼ����ժҪ����
	 */
	public ChartDefineDigest[] getChartDefineDigests();
}
