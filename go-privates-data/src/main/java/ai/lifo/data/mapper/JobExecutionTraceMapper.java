package ai.lifo.data.mapper;

import ai.lifo.data.entity.JobExecutionTraceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author zhanglifeng
 * @since 2024-05-23
 */
@Mapper
public interface JobExecutionTraceMapper {

    JobExecutionTraceEntity selectByPrimaryKey(Integer id);

    int insert(JobExecutionTraceEntity jobExecutionTraceEntity);

    Optional<JobExecutionTraceEntity> findOneByCondition(@Param("condition") JobExecutionTraceEntity jobExecutionTraceEntity,
                                                         @Param("startTime") Date startTime,
                                                         @Param("endTime") Date endTime);

    List<JobExecutionTraceEntity> findListByCondition(@Param("condition") JobExecutionTraceEntity jobExecutionTraceEntity,
                                                      @Param("startTime") Date startTime,
                                                      @Param("endTime") Date endTime,
                                                      @Param("startId") Integer startId,
                                                      @Param("size") int size);


    int insertSelective(JobExecutionTraceEntity jobExecutionTraceEntity);

    int updateByPrimaryKeySelective(JobExecutionTraceEntity jobExecutionTraceEntity);

    int updateByPrimaryKey(JobExecutionTraceEntity jobExecutionTraceEntity);
}
