package ai.lifo.xd.late.exception;

/**
 * 系统异常类
 * 
 * @author cyy
 * 
 * @date 2019-02-20 10:24:23
 * 
 */
public class SysException extends AppRuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * @param message
	 */
	public SysException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SysException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SysException(String message, Throwable cause) {
		super(cause);
	}

}