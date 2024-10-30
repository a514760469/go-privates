package ai.lifo.goelasticjob;

import ai.lifo.common.LogUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zhanglifeng
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class GoElasticJobApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GoElasticJobApplication.class, args);
        LogUtil.logApplicationStartup(context.getEnvironment());
    }
}
