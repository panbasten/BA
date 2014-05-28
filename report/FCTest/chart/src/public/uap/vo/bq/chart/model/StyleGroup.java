package uap.vo.bq.chart.model;

import com.ufida.iufo.pub.tools.AppDebug;

/**
 * 风格组
 * @author zhanglld
 *
 */
public class StyleGroup extends PropertyGroup{
	private static final long serialVersionUID = 1L;
	
	/*应用到fusionchart 上的对象，如 Caption Subcaption 等对象*/
	private String applyObject;
		
	public StyleGroup(String refCode) {
		super(refCode);
	}
	
	
	@Override
	public String getCode(){
//		if(super.getCode() == null){
			return this.getRefCode() + "_" + this.getApplyObject();
			//super.setCode(getRefCode() + "_" + this.applyObject);
//			AppDebug.debug(this.getClass().toString() + ": code不能为空，请解决问题！");
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
