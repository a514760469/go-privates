package ai.lifo.goelasticjob.trace;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
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
        System.out.println("joinPoint = " + joinPoint);
        System.err.println("joinPoint = " + joinPoint.getThis());
        return joinPoint.proceed();
    }
}
