package ai.lifo.goelasticjob.web;

import ai.lifo.goelasticjob.common.SpringUtils;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.OneOffJobBootstrap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhanglifeng
 * @since 2024-02-21
 */
@RestController
public class OneOffJobController {

    @GetMapping("/execute")
    public String executeOneOffJob() {
        SpringUtils.<OneOffJobBootstrap>getBean("myOneOffJob").execute();
        return "success";
    }

}
