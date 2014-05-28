package uap.vo.bq.chart.define;

import java.io.Serializable;

/**
 * ��ѡ��
 * 
 * @author zhanglld
 *
 */
public class ConstraintTerm  implements Serializable, Cloneable{
	private static final long serialVersionUID = 204L;
	
	/*�޶���Χ��ֵ��ö��ֵ��*/
	private String value;
	/*�޶���Χ ����*/
	private String name;
	
	public ConstraintTerm(String value, String name){
		this.name = name;
		this.value = value;
	}
	
	public String getValue() {
		return value;// nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0",value);
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Object clone() {
		ConstraintTerm newConstraintTerm = null;
		try {
			newConstraintTerm = (ConstraintTerm)super.clone();
			newConstraintTerm.name = this.name;
			newConstraintTerm.value = this.value;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
		
		return newConstraintTerm;
	}
}
