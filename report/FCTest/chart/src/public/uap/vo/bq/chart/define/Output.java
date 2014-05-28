package uap.vo.bq.chart.define;

import java.io.Serializable;


public class Output implements Serializable, Cloneable{
	private static final long serialVersionUID = 205L;
	private String typeClassName;
	
	public Output(String typeClassName){
		this.typeClassName = typeClassName;
	}

	public String getTypeClassName() {
		return typeClassName;
	}
	
	@Override
	public Object clone() {
		Output newOutput = null;
		try {
			newOutput = (Output)super.clone();
			newOutput.typeClassName = this.typeClassName;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException (e);
		}
	
		return newOutput;
	}
}
