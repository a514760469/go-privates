package ai.lifo.goelasticjob.web;

import ai.lifo.goelasticjob.trace.JobOperator;
import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.elasticjob.infra.pojo.JobConfigurationPOJO;
import org.apache.shardingsphere.elasticjob.lite.internal.snapshot.SnapshotService;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final CoordinatorRegistryCenter coordinatorRegistryCenter;

    @Autowired(required = false)
    private SnapshotService snapshotService;

    @GetMapping("/getJobConfig")
    public JobConfigurationPOJO getJobConfig(String jobName) throws ParseException {
        return jobOperator.getJobConfig(jobName);
    }


    @GetMapping("/dump/{jobName}")
    public String dumpJob(@PathVariable String jobName) {
//        return snapshotService.dumpJobDirectly(jobName);
        return null;
    }



}
