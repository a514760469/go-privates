package ai.lifo.goelasticjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author zhanglifeng
 */
@EnableAspectJAutoProxy
@SpringBootApplication
public class GoElasticJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoElasticJobApplication.class, args);
    }
}
