package uap.vo.bq.chart.define;

import java.io.Serializable;

import uap.util.bq.chart.ServerInfoUtil;


public class Icon  implements Serializable, Cloneable{
	private static final long serialVersionUID = 100L;
	private String littleIcon;
	private String mouseOverIcon;
	private String bigIcon;
	private String pressedIcon;
	private String chartDefineCode;
	private String baseURL;
	
	public Icon(IconInfo info){
		this.littleIcon = info.littleIcon;
		this.mouseOverIcon = info.mouseOverIcon;
		this.bigIcon = info.bigIcon;
		this.pressedIcon = info.pressedIcon;
		this.chartDefineCode = info.chartDefineCode;
	}
	
	public String getLittleIconURL() {
		if(baseURL == null){
			baseURL = getBaseURL();
		}
		return baseURL + "/" + littleIcon;
	}
	
	private String getBaseURL(){
		String serverRoot = ServerInfoUtil.getServerRootURL();
		return serverRoot + "/chartDefines/" + chartDefineCode;
	}
	
	public String getLittleIcon() {		
		return littleIcon;
	}

	public String getMouseOverIcon() {
		return mouseOverIcon;
	}
	
	public String getMouseOverIconURL() {
		if(baseURL == null){
			baseURL = getBaseURL();
		}
		return baseURL + "/" + mouseOverIcon;
	}
	
	public String getBigIcon(){
		return bigIcon;
	}
	
	public String getBigIconURL(){
		if(baseURL == null){
			baseURL = getBaseURL();
		}
		return baseURL + "/" + bigIcon;
	}
	
	public String getPressedIcon(){
		return this.pressedIcon;
	}
	
	public String getPressedIconURL(){
		return this.pressedIcon;
	}
	
	public static class IconInfo{
		public String littleIcon;
		public String mouseOverIcon;
		public String bigIcon;
		public String pressedIcon;
		public String chartDefineCode;
	}
	
	@Override
	public Object clone() {
		Icon newIcon = null;
		try {
			newIcon = (Icon)super.clone();
			newIcon.baseURL = this.baseURL;
			newIcon.bigIcon = this.bigIcon;
			newIcon.chartDefineCode = this.chartDefineCode;
			newIcon.littleIcon = this.littleIcon;
			newIcon.mouseOverIcon = this.mouseOverIcon;
			newIcon.pressedIcon = this.pressedIcon;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException (e);
		}
		
		return newIcon;
	}
}
