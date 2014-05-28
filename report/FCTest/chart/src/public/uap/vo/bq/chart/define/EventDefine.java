package uap.vo.bq.chart.define;

import java.io.Serializable;

import com.ufida.iufo.pub.tools.DeepCopyUtilities;

/**
 * 事件定义
 * @author zhanglld
 *
 */
public class EventDefine implements Serializable, Cloneable{
	private static final long serialVersionUID = 101L;
	private String code;
	private String name;
	private Input[] inputs;
	
	public EventDefine(String code, String name, Input[] inputs){
		this.code = code;
		this.name = name;
		this.inputs = inputs;
	}
	
	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Input[] getInputs() {
		return inputs;
	}
	
	@Override
	public Object clone() {
		EventDefine newEventDefine = null;
		try {
			newEventDefine = (EventDefine)super.clone();
			newEventDefine.code = this.code;
			newEventDefine.name = this.name;
			newEventDefine.inputs = (Input[]) DeepCopyUtilities.copy(this.inputs);
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException (e);
		}
		
		return newEventDefine;
	}
}
