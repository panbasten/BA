package uap.vo.bq.chart.model;

/**
 * �¼�
 * 
 * @author zhanglld
 *
 */
public class ChartEvent {
	private String code;
	private Object[] parameters;
	
	public ChartEvent(String code, Object[] parameters){
		this.code = code;
		this.parameters = parameters;
	}
	
	public String getCode() {
		return code;
	}

	public Object[] getParameters() {
		return parameters;
	}
}
