package uap.vo.bq.chart.model;

/**
 * 属性组分类标示枚举
 * @author wangqzh
 *
 */
public enum AssortmentEnum {
	PROPERTY("property"), CATEGORY("category"), SERIES("series"), STYLES("styles"), TRENDLINE("trendline"), VLINE("vline"), DATASET("dataset");
	private String value;
	private AssortmentEnum(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
//	public boolean equals(String value){
//		return this.value.equals(value);
//	}
}
