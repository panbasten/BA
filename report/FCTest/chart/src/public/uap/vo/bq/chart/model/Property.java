package uap.vo.bq.chart.model;

import uap.bq.chart.utils.ChartParamUtils;

/**
 * 属性
 * 
 * @author zhanglld
 * 
 */
public class Property extends AbstractModelElement<IModelObject> {
	private static final long serialVersionUID = 1L;

	/* 引用chartdefine中code 属性标志 */
	private String refCode;
	/* refCode 对应的属性的值 */
	private String value;
	/* 是否生成该属性数据 */
	private boolean generate = true;
	/**
	 * 参数参照表达式
	 */
	private String refExp = null;
	/**
	 * 多语code
	 */
	private String mulCode = null;

	public Property(String refCode) {
		this.refCode = refCode;
		setCode(refCode);
	}

	public String getRefCode() {
		return refCode;
	}

	public String getValue() {
		// 兼容问题
		if (this.refExp == null && ChartParamUtils.isRefParam(value)) {
			this.refExp = value;
		}

		return value;
	}

	/**
	 * 
	 * @Description: 编辑器使用
	 */
	public void setValue(String value) {
		if (this.value == null && value == null) {
			return;
		}
		if (this.value != null && this.value.equals(value)) {
			return;
		}
		if (ChartParamUtils.isRefParam(value)) {
			this.refExp = value;
		} else {
			this.refExp = null;
		}
		this.value = value;
		this.mulCode = null;
		this.setChange(true);
	}

	/**
	 * 
	 * @Description: 供多语使用
	 */
	public void setMulValue(String mulValue) {
		this.value = mulValue;
		if (ChartParamUtils.isRefParam(value)) {
			this.refExp = value;
		}
		this.setChange(true);
	}

	/**
	 * 
	 * @Description: 供计算用
	 */
	public void setValue4Calc(String calcRes) {
		this.value = calcRes;		
		this.setChange(true);
	}

	public boolean isGenerate() {
		return generate;
	}

	public void setGenerate(boolean generate) {
		this.generate = generate;
		this.setChange(true);
	}

	public String getMulCode() {
		return mulCode;
	}

	public void setMulCode(String mulCode) {
		this.mulCode = mulCode;
	}

	public String getRefExp() {
		return refExp;
	}
	public void setRefExp(String refExp) {
		this.refExp = refExp;
	}

	@Override
	public Object clone() {
		Property newProperty = (Property) super.clone();
		newProperty.generate = this.generate;
		newProperty.refCode = this.refCode;
		newProperty.refExp = this.refExp;
		newProperty.value = this.value;
		return newProperty;
	}

}
