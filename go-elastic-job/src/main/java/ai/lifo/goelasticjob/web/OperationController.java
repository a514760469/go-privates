package ai.lifo.goelasticjob.web;

import ai.lifo.goelasticjob.trace.JobOperator;
import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.elasticjob.infra.pojo.JobConfigurationPOJO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author zhanglifeng
 * @since 2024-05-23
 */
@RestController
@RequiredArgsConstructor
public class OperationController {

    private final JobOperator jobOperator;

    @GetMapping("/getJobConfig")
    public JobConfigurationPOJO getJobConfig(String jobName) throws ParseException {
        return jobOperator.getJobConfig(jobName);
    }

}
