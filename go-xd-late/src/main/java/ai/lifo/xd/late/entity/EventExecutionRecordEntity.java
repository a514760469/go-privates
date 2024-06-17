package ai.lifo.xd.late.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 事件执行记录表
 *
 * @author zhanglifeng
 * @since 2024-05-29 
 */
@Data
public class EventExecutionRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     *
     */
    private String jobName;

    /**
     * 当前日期
     */
    private Date recordDate;

    /**
     * 资方
     */
    private Integer capitalSource;

    /**
     * 执行次数
     */
    private Integer executeTimes;

    /**
     * 失败次数
     */
    private Integer failedTimes;

    /**
     *
     */
    private Date createdAt;

    /**
     *
     */
    private Date updatedAt;
}