package uap.vo.bq.chart.define;

import java.io.Serializable;

/**
 * ���Ӧ�ö���
 * 
 * @author zhanglld
 *
 */
public class StyleApplyObject implements Serializable{
	private static final long serialVersionUID = 659961895060404016L;
	
	/*Ӧ�ö���ı�ʾ��*/
	private String code;
	/*Ӧ�ö��������*/
	private String name;
	/*��ʽ������������*/
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
