package uap.vo.bq.chart.define;

import java.io.Serializable;


public class Input  implements Serializable, Cloneable{
	private static final long serialVersionUID = 206L;
	/*标示符*/
	private String code;
	/*名称*/
	private String name;
	/*类型名称*/
	private String typeClassName;
	
	public Input(String code, String name, String typeClassName){
		this.code = code;
		this.name = name;
		this.typeClassName = typeClassName;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getTypeClassName() {
		return typeClassName;
	}
	
	@Override
	public Object clone() {
		Input newInput = null;
		try {
			newInput = (Input)super.clone();
			newInput.code = this.code;
			newInput.name = this.name;
			newInput.typeClassName = this.typeClassName;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException (e);
		}
		
		return newInput;
	}
}
