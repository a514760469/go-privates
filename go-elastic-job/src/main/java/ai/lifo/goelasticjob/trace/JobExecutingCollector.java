package ai.lifo.goelasticjob.trace;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 收集job执行记录
 * @author zhanglifeng
 * @since 2024-05-22
 */
@Aspect
@Component
@RequiredArgsConstructor
public class JobExecutingCollector {

    @Pointcut("execution(* org.apache.shardingsphere.elasticjob.simple.job.SimpleJob.execute(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Object target = joinPoint.getTarget();
        Object[] args = joinPoint.getArgs();

        // 获取代理之前的目标对象
        Class<?> ultimateTarget = AopProxyUtils.ultimateTargetClass(target);

        // 获取调用者信息
        String caller = ultimateTarget.getName();

        System.out.println("Method called: " + method.getName());
        System.out.println("Caller: " + caller);
        System.out.println("Arguments: ");
        for (Object arg : args) {
            System.out.println(" - " + arg);
        }
        Object result = joinPoint.proceed();

        System.out.println("Method return: " + result);

        return result;
    }
}
