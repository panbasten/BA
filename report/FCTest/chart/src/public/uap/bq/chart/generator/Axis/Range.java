package uap.bq.chart.generator.Axis;

import java.util.Collection;

import uap.vo.bq.chart.model.dataset.ChartBodyRow;
import uap.vo.bq.chart.model.dataset.ChartDataCell;
import uap.vo.bq.chart.model.dataset.ChartDataset;

import com.ufida.iufo.pub.tools.AppDebug;

public class Range {

	public Range(double maxvalue, double minvalue) {
		this.maxvalue = maxvalue;
		this.minvalue = minvalue;
	}
	
	static final double MAX = Math.pow(2, 60);
	static final double MIN = -MAX;
	

	/*
	 * ���ֵ
	 */
	public final double maxvalue;
	/*
	 * ��Сֵ
	 */
	public final double minvalue;

	/**
	 * ��ȡ���ݼ�ָ���е��������ݵ����ֵ��Сֵ
	 * 
	 * @param dataset
	 * @param collection
	 * @return
	 */
	public static Range getRange(ChartDataset dataset,
			Collection<String> collection) {
		double minvalue = MAX;
		double maxvalue = MIN;
		String valuetemp;
		double temp;
		for (ChartBodyRow cbr : dataset.getChartBody().getChartBodyRows()) {
			for (String columnCode : collection) {
				ChartDataCell datacell = cbr.getChartDataCell(columnCode);
				if (datacell == null) {
					AppDebug.debug("One datacell is null!");
					continue;
				}

				valuetemp = datacell.getCaption();
				if (valuetemp == null || valuetemp.equals(""))
					continue;

				temp = Double.parseDouble(valuetemp);
				minvalue = minvalue > temp ? temp : minvalue;
				maxvalue = maxvalue > temp ? maxvalue : temp;
			}
		}
		if (Math.abs(minvalue - MIN) < Math.pow(10, -6)
				|| Math.abs(maxvalue - MAX) < Math.pow(10,-6))
			return null;

		return new Range(maxvalue, minvalue);
	}

}
