package uap.vo.bq.chart.model;

import com.ufida.iufo.pub.tools.AppDebug;

/**
 * �����
 * @author zhanglld
 *
 */
public class StyleGroup extends PropertyGroup{
	private static final long serialVersionUID = 1L;
	
	/*Ӧ�õ�fusionchart �ϵĶ����� Caption Subcaption �ȶ���*/
	private String applyObject;
		
	public StyleGroup(String refCode) {
		super(refCode);
	}
	
	
	@Override
	public String getCode(){
//		if(super.getCode() == null){
			return this.getRefCode() + "_" + this.getApplyObject();
			//super.setCode(getRefCode() + "_" + this.applyObject);
//			AppDebug.debug(this.getClass().toString() + ": code����Ϊ�գ��������⣡");
//		}
		
//		return super.getCode();
	}

	public String getApplyObject() {
		return this.applyObject ;//nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("0502004_0",this.applyObject);
	}

	public void setApplyObject(String applyObject) {
		this.applyObject = applyObject;
		this.setChange(true);
	}
		
}
