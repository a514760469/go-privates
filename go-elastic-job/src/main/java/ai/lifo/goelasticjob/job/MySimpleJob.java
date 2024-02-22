package ai.lifo.goelasticjob.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * ai.lifo.goelasticjob.job.MySimpleJob
 * @author zhanglifeng
 * @since 2024-02-04
 */
@Slf4j
@Service
public class MySimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {

        switch (shardingContext.getShardingItem()) {
            case 0:
                // do something by sharding item 0
                System.out.println("do something by sharding item 0");
                doJob(0);
                break;
            case 1:
                // do something by sharding item 1
                System.out.println("do something by sharding item 1");
                doJob(1);
                break;
            case 2:
                System.out.println("do something by sharding item 2");
                doJob(2);
                break;
            // case n: ...
        }

    }

    private void doJob(int itemId) {
        for (int i = 0; i < 10; i++) {
            System.out.println(itemId + ": 执行任务" + i + "," + LocalDateTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
