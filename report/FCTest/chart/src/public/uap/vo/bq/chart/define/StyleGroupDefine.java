package uap.vo.bq.chart.define;

import java.io.Serializable;

/**
 * ���Ӧ�������鶨��
 * @author zhanglld
 *
 */
public class StyleGroupDefine extends PropertyGroupDefine implements Serializable{
	private static final long serialVersionUID = -1596179211907645318L;
	private StyleApplyObject[] styleApplyObjects;
	
	/*  ֧�� fusionchart font animation shadow ���ض����ͱ�ʾ */
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
