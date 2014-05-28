package uap.vo.bq.chart.model;

import java.io.Serializable;

/**
 * ͳ��ͼʵ��ö��
 * @author zhanglld
 *
 */
public enum ChartImplType implements Cloneable, Serializable{
	FUSIONCHARTS("FusionCharts", "FusionCharts"),
	HICHARTS("HiCharts", "HiCharts");
	
	private String code;
	private String name;
	
	private ChartImplType(String code, String name){
		this.code = code;
		this.name = name;
	}
	
	public String getCode(){
		return code;
	}
	
	public String toString(){
		return code;
	}
	
	public String getName(){
		return name;
	}
}
