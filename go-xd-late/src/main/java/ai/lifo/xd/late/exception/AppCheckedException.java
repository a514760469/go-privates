package ai.lifo.xd.late.exception;

/**
 * 非运行时异常底层抽象类
 * 
 * @author cyy
 * 
 * 2019-02-20 10:19:23
 * 
 */
public abstract class AppCheckedException extends Exception {

	private static final long serialVersionUID = 1L;

	public AppCheckedException() {
		super();
	}

	public AppCheckedException(String msg) {
		super(msg);
	}

	public AppCheckedException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AppCheckedException(Throwable cause) {
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
