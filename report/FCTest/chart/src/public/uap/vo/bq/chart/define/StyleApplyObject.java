package uap.vo.bq.chart.define;

import java.io.Serializable;

/**
 * 风格应用对象
 * 
 * @author zhanglld
 *
 */
public class StyleApplyObject implements Serializable{
	private static final long serialVersionUID = 659961895060404016L;
	
	/*应用对象的标示符*/
	private String code;
	/*应用对象的名称*/
	private String name;
	/*样式包含的属性组*/
	private String[] includeGroups;
	
	public StyleApplyObject(String code, String name, String[] includeGroups){
		this.code = code;
		this.name = name;
		this.includeGroups = includeGroups;
	}
		
	public String getCode() {
		return code;
	}
	public String getName() {
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String[] getIncludeGroups() {
		return includeGroups;
	}	
}
