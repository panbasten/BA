package uap.vo.bq.chart.model;

import nc.vo.pub.BusinessException;

/**
 * 处理变更的异常
 * 
 * @author zhanglld
 *
 */
public class ChangeHandleException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public ChangeHandleException() {
	}

	public ChangeHandleException(String s) {
		super(s);
	}

	public ChangeHandleException(Throwable cause) {
		super(cause);
	}

	public ChangeHandleException(String s, String errorCode) {
		super(s, errorCode);
	}

	public ChangeHandleException(String message, Throwable cause) {
		super(message, cause);
	}

}
