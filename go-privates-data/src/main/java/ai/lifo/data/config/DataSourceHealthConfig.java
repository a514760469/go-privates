package ai.lifo.data.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 */
@SpringBootConfiguration
public class DataSourceHealthConfig {

    @Bean
    public DataSourceHealthIndicator createIndicator(DataSource shardingDataSource) {
        return new DataSourceHealthIndicator(shardingDataSource, "select 1");
    }
}

