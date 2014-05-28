package uap.bq.chart.component;

import nc.vo.pub.BusinessRuntimeException;

/**
 * 本地Swing接口发生异常
 * @author zhanglld
 *
 */
public class NativeSwingException extends BusinessRuntimeException{
	private static final long serialVersionUID = 1L;
	
	public NativeSwingException(String msg, Throwable cause){
		super(msg, cause);
	}
	
	public NativeSwingException(String msg){
		super(msg);
	}
	
	public NativeSwingException(Throwable cause){
		super(cause.getMessage(), cause);
	}
}
