package uap.vo.bq.chart.define;

import java.io.Serializable;

/**
 * 风格应用属性组定义
 * @author zhanglld
 *
 */
public class StyleGroupDefine extends PropertyGroupDefine implements Serializable{
	private static final long serialVersionUID = -1596179211907645318L;
	private StyleApplyObject[] styleApplyObjects;
	
	/*  支持 fusionchart font animation shadow 等特定类型标示 */
	private String rawType = "";

	public StyleGroupDefine(StyleGroupDefineInfo info) {
		super(info);
		this.styleApplyObjects = info.styleApplyObjects;
		this.rawType = info.rawType;
	}
	
	public StyleApplyObject[] getStyleApplyObjects() {
		return styleApplyObjects;
	}

	public static class StyleGroupDefineInfo extends
			PropertyGroupDefine.PropertyGroupDefineInfo {
		public StyleApplyObject[] styleApplyObjects;
		public String rawType;
	}

	
	public String getRawType(){
		return rawType;
	}
}
