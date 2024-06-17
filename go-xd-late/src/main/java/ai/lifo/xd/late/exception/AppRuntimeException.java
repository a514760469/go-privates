package ai.lifo.xd.late.exception;

/**
 * 运行时异常底层抽象类
 * 
 * @author cyy
 * 2019-02-20 10:23:33
 * 
 */
public abstract class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AppRuntimeException() {
		super();
	}

	public AppRuntimeException(String msg) {
		super(msg);
	}

	public AppRuntimeException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AppRuntimeException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return ExceptionUtils.buildMessage(super.getMessage(), getCause());
	}

	public Throwable getRootCause() {
		return ExceptionUtils.getRootCause(this, true);
	}

	public Throwable getMostSpecificCause() {
		return ExceptionUtils.getRootCause(this, false);
	}

	public boolean contains(Class<? extends Throwable> exType) {
		return ExceptionUtils.contains(this, exType);
	}
}
