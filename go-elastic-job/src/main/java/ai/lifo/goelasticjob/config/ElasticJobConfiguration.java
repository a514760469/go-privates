package ai.lifo.goelasticjob.config;

import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.elasticjob.lite.lifecycle.api.JobConfigurationAPI;
import org.apache.shardingsphere.elasticjob.lite.lifecycle.api.JobOperateAPI;
import org.apache.shardingsphere.elasticjob.lite.lifecycle.internal.operate.JobOperateAPIImpl;
import org.apache.shardingsphere.elasticjob.lite.lifecycle.internal.settings.JobConfigurationAPIImpl;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhanglifeng
 * @since 2024-05-23
 */
@Configuration
@RequiredArgsConstructor
public class ElasticJobConfiguration {

    private final CoordinatorRegistryCenter coordinatorRegistryCenter;

    @Bean
    public JobConfigurationAPI jobConfigurationAPI() {
        return new JobConfigurationAPIImpl(coordinatorRegistryCenter);
    }

    @Bean
    public JobOperateAPI jobOperateAPI() {
        return new JobOperateAPIImpl(coordinatorRegistryCenter);
    }

}
