package ai.lifo.goelasticjob.trace;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.error.handler.JobErrorHandler;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * @author zhanglifeng
 * @since 2024-05-23
 */
@Slf4j
@Service
public class GoElasticJobErrorHandler implements JobErrorHandler {

    @Override
    public void handleException(String jobName, Throwable cause) {
        System.err.println("GoElasticJobErrorHandler. handleException job: " + jobName);
        log.error(cause.getMessage(), cause);
    }

    @Override
    public void init(Properties props) {
        // log.info("init: " + JSONObject.toJSONString(props));
    }

    @Override
    public String getType() {
        return this.getClass().getName();
    }
}
