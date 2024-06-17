package ai.lifo.goelasticjob.trace;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
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
        System.out.println("method.getName() = " + method.getName());
        System.out.println("joinPoint = " + joinPoint);
        System.err.println("joinPoint = " + joinPoint.getThis());
        return joinPoint.proceed();
    }
}
