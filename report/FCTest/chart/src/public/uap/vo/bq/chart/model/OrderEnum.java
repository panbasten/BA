package uap.vo.bq.chart.model;

import nc.vo.pub.BusinessRuntimeException;

/**
 * 排序枚举
 * @author zhanglld
 *
 */
public enum OrderEnum {
	ORDER("order"), REVERSE_ORDER("reverseOrder");

	private String value;

	private OrderEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

//	public boolean equals(String value){
//		return this.value.equals(value);
//	}

	public static OrderEnum genOrder(String value){
		if(ORDER.getValue().equals(value)){
			return ORDER;
		} else if(REVERSE_ORDER.getValue().equals(value)) {
			return REVERSE_ORDER;
		} else if (value == null) {
			return null;
		} else {
			throw new BusinessRuntimeException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0","00502004-0083")/*@res "不正确的排序枚举值："*/ + value);
		}
	}

	@Override
	public String toString(){
		return value;
	}
}