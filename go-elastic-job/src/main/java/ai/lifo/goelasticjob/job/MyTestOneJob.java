package ai.lifo.goelasticjob.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Service;

/**
 * @author zhanglifeng
 * @since 2024-02-21
 */
@Slf4j
@Service
public class MyTestOneJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("MyOneOffJob ！！！！");

        // 作业名称
        String jobName = shardingContext.getJobName();
        // 分片序列号
        int shardingItem = shardingContext.getShardingItem();
        // 分片序列号对应的value值
        String shardingParameter = shardingContext.getShardingParameter();
        // 作业分片总数
        int shardingTotalCount = shardingContext.getShardingTotalCount();

        log.info("{} begin, 作业分片总数: {}, 分片序列号: {},分片序列号对应的value值：{}",
                jobName,
                shardingTotalCount,
                shardingItem,
                shardingParameter);

    }
}
