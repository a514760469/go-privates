package ai.lifo.goelasticjob.job;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zhanglifeng
 * @since 2024-02-21
 */
@Slf4j
@Service
public class MyDataflowJob implements DataflowJob<String> {

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        log.info("dataflow job fetch Data 开始：");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String string = UUID.randomUUID().toString();
            list.add(string);
        }
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> data) {
        log.info("消费dataflow job 数据：");
        data.forEach(s -> log.info("消费dataflow job 数据：{}", s));
    }
}
