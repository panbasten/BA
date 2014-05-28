package uap.vo.bq.chart.model;

import uap.bq.chart.utils.ChartParamUtils;

/**
 * ����
 * 
 * @author zhanglld
 * 
 */
public class Property extends AbstractModelElement<IModelObject> {
	private static final long serialVersionUID = 1L;

	/* ����chartdefine��code ���Ա�־ */
	private String refCode;
	/* refCode ��Ӧ�����Ե�ֵ */
	private String value;
	/* �Ƿ����ɸ��������� */
	private boolean generate = true;
	/**
	 * �������ձ��ʽ
	 */
	private String refExp = null;
	/**
	 * ����code
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
		// ��������
		if (this.refExp == null && ChartParamUtils.isRefParam(value)) {
			this.refExp = value;
		}

		return value;
	}

	/**
	 * 
	 * @Description: �༭��ʹ��
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
	 * @Description: ������ʹ��
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
	 * @Description: ��������
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
