package ai.lifo.xd.late;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author zhanglifeng
 * @since 2024-05-24
 */
@Slf4j
@MapperScan("ai.lifo.xd.late")
@SpringBootApplication
public class XdLateWebApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(XdLateWebApplication.class);
        Environment env = context.getEnvironment();
        log.info("====================================================================");
        log.info("启动环境: {}", env.getProperty("spring.profiles.active"));
        log.info("启动端口: {}", env.getProperty("server.port"));
        log.info("日志等级: {}", env.getProperty("logback.level"));
        log.info("Startup complete ...");
        log.info("====================================================================");
    }
}
