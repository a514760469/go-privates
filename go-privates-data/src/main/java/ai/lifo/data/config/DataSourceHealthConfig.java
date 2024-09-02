package ai.lifo.data.config;

import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.MasterSlaveDataSource;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.List;

/**
 */
@SpringBootConfiguration
@RequiredArgsConstructor
public class DataSourceHealthConfig {

    private final List<DataSource> dataSources;

    /**
     * bean name 指定 dbHealthIndicator，否则会有两个 DataSourceHealthIndicator
     * @see org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration#dbHealthContributor
     */
    @Bean(name = "dbHealthIndicator")
    public DataSourceHealthIndicator createIndicator(DataSource shardingDataSource) {
//        DataSource master = ((MasterSlaveDataSource) shardingDataSource).getDataSourceMap().get("master");
        return new DataSourceHealthIndicator(shardingDataSource, "select 1");
    }
}

