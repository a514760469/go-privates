package ai.lifo.xd.late.exception;

import lombok.Getter;

/**
 * 业务错误返回信息实现类
 * 
 * @author cyy
 * 
 * @date 2019-01-22 10:24:23
 * 
 */
@Getter
public class BusinessException extends AppRuntimeException {

	private static final long serialVersionUID = 1L;

	private final String code;

	private final String msg;

	/**
	 * @param alert
	 */
//	public BusinessException(AlertMessage alert) {
//		super(alert.getMsg());
//		this.code = alert.getCode();
//		this.msg = alert.getMsg();
//	}

	/**
	 * 
	 * @param alert
	 * @param message
	 */
//	public BusinessException(AlertMessage alert, String message) {
//		super(message);
//		this.code = alert.getCode();
//		this.msg = alert.getMsg();
//	}

	/**
	 * 
	 * @param code
	 * @param msg
	 */
	public BusinessException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

}