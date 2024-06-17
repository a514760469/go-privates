package ai.lifo.xd.late.module;

import ai.lifo.xd.late.entity.JobExecutionTraceEntity;
import ai.lifo.xd.late.mapper.EventExecutionRecordMapper;
import ai.lifo.xd.late.mapper.JobExecutionTraceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author zhanglifeng
 * @since 2024-05-29
 */
@Service
@RequiredArgsConstructor
public class TestDataModule {

    private final EventExecutionRecordMapper eventExecutionRecordMapper;

    private final JobExecutionTraceMapper jobExecutionTraceMapper;

    public void testDB() {

//        eventExecutionRecordMapper.insert();
    }

}
