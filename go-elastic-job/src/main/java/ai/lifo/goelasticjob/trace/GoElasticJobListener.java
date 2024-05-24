package ai.lifo.goelasticjob.trace;

import com.alibaba.fastjson.JSONObject;
import org.apache.shardingsphere.elasticjob.infra.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.infra.listener.ShardingContexts;
import org.springframework.stereotype.Component;

/**
 * @author zhanglifeng
 * @since 2024-05-22
 */
@Component
public class GoElasticJobListener implements ElasticJobListener {

    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        System.err.println("beforeJobExecuted");
        System.out.println(JSONObject.toJSONString(shardingContexts));
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        System.err.println("afterJobExecuted");
        System.out.println(JSONObject.toJSONString(shardingContexts));
    }

    @Override
    public String getType() {
        return this.getClass().getName();
    }
}
