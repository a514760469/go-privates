package ai.lifo.data.mapper;


import ai.lifo.data.entity.EventExecutionRecordEntity;

import java.util.Optional;

/**
 * @author zhanglifeng
 * @since 2024-05-29 
 */
public interface EventExecutionRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(EventExecutionRecordEntity record);

    int insertSelective(EventExecutionRecordEntity record);

    EventExecutionRecordEntity selectByPrimaryKey(Integer id);

    Optional<EventExecutionRecordEntity> findOneByCondition(EventExecutionRecordEntity condition);

    int updateByPrimaryKeySelective(EventExecutionRecordEntity record);

    int updateByPrimaryKey(EventExecutionRecordEntity record);
}