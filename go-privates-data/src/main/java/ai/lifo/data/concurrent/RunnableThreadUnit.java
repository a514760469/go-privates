package ai.lifo.data.concurrent;

import ai.lifo.entity.Execute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

/**
 * 类 名: RunnableThreadUnit <br>
 * 描 述: 线程单元（无返回值） <br>
 * 线程中执行目标对象的目标方法
 */
@Slf4j
public class RunnableThreadUnit implements Runnable {

	private final Object object;
	private final String methodName;
	private final Object[] methodParameters;

	public RunnableThreadUnit(Object object, String methodName, Object... methodParameters) {
		if (object == null || StringUtils.isBlank(methodName) || methodParameters == null) {
			throw new IllegalStateException("init runnable thread unit error...");
		}
		this.object = object;
		this.methodName = methodName;
		this.methodParameters = methodParameters;
	}

	/**
	 * 通过反射的方式调用方法
	 */
	@Override
	public void run() {
		try {
			Class<?>[] classes = new Class[methodParameters.length];
			for (int i = 0; i < methodParameters.length; i++) {
				classes[i] = methodParameters[i].getClass();
			}
			Method method = object.getClass().getMethod(methodName, classes);
			method.invoke(object, methodParameters);
		} catch (Exception e) {
			log.error("execute runnable thread unit error... service=[{}], invokeMethodName=[{}], error:{}",
					object, methodName, e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		Execute execute = new Execute();
		Thread thread = new Thread(new RunnableThreadUnit(execute, "exec", 10, "test"));
		thread.start();
	}

}
