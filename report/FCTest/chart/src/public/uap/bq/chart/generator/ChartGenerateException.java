package uap.bq.chart.generator;

import nc.vo.pub.BusinessException;

public class ChartGenerateException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ChartGenerateException(String msg, Exception e) {
		super(msg, e);
	}
	public ChartGenerateException(String msg){
		super(msg);
	}

}
