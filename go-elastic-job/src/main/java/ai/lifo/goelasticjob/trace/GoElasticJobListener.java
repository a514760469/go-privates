package ai.lifo.goelasticjob.trace;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.infra.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.infra.listener.ShardingContexts;
import org.springframework.stereotype.Component;

/**
 * @author zhanglifeng
 * @since 2024-05-22
 */
@Slf4j
@Component
public class GoElasticJobListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        log.info("beforeJobExecuted");
        log.info(JSONObject.toJSONString(shardingContexts));
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        log.info("afterJobExecuted");
        log.info(JSONObject.toJSONString(shardingContexts));
    }

    @Override
    public String getType() {
        return this.getClass().getName();
    }
}
