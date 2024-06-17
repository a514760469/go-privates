package ai.lifo.xd.late.module;

import ai.lifo.xd.late.entity.JobExecutionTraceEntity;
import ai.lifo.xd.late.mapper.JobExecutionTraceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * @author zhanglifeng
 * @since 2024-05-29
 */
@SpringBootTest
class TestDataModuleTest {

    @Autowired
    JobExecutionTraceMapper jobExecutionTraceMapper;


    @Test
    public void testInert() {
        JobExecutionTraceEntity condition = new JobExecutionTraceEntity();
        condition.setCapitalSource(2082);
        Optional<JobExecutionTraceEntity> one = jobExecutionTraceMapper.findOneByCondition(condition, null, null);
        one.ifPresent(System.out::println);
    }

}