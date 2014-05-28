package uap.itf.bq.chart;

import nc.vo.pub.format.exception.FormatException;
import uap.vo.bq.chart.layout.Layout;

/**
 * ���ַ���  �÷���ֻ�� cache ʹ��
 * 
 * @author zhanglld
 *
 */
public interface ILayoutService {
	/**
	 * ����ͳ��ͼ��������ò��ֶ���
	 * 
	 * @param chartDefineCode
	 * @return
	 * @throws FormatException
	 */
	Layout getLayout(String chartDefineCode) throws FormatException;
}
