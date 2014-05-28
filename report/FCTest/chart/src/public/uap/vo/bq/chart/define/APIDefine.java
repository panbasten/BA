package uap.vo.bq.chart.define;

import java.io.Serializable;

import com.ufida.iufo.pub.tools.DeepCopyUtilities;

public class APIDefine  implements Serializable, Cloneable{
	private static final long serialVersionUID = 205L;
	private Input[] inputs;
	private String code;
	private String name;
	private Output output;
	
	public APIDefine( String code, String name, Input[] inputs, Output output){
		this.code = code;
		this.name = name;
		this.inputs = inputs;
		this.output = output;
	}
	
	public Input[] getInputs() {
		return inputs;
	}
	public Output getOutput() {
		return output;
	}
	public void setInputs( Input[] inputs) {
		this.inputs = inputs;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public Object clone() {
		APIDefine newAPIDefine = null;
		try {
			newAPIDefine = (APIDefine)super.clone();
			newAPIDefine.code = this.code;
			newAPIDefine.name = this.name;
			newAPIDefine.output = (Output) this.output.clone();
			newAPIDefine.inputs = (Input[]) DeepCopyUtilities.copy(this.inputs);
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException (e);
		}
		
		return newAPIDefine;
	}
}
