package ai.lifo.goelasticjob.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Service;

/**
 * @author zhanglifeng
 * @since 2024-05-23
 */
@Slf4j
@Service
public class ThrownTestJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        throw new RuntimeException("测试Job异常");
    }
}
