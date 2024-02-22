package ai.lifo.goelasticjob.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhanglifeng
 */
@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext context = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) context.getBean(beanName);
	}

	public static <T> T getBean(Class<T> clazz) {
		return (T) context.getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return context.getBean(name, clazz);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		return context.getBeansOfType(clazz);
	}

}