package ai.lifo.xd.late.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zhanglifeng
 * @since 2024-05-23
 */
@Data
public class JobExecutionTraceEntity implements Serializable {

    private static final long serialVersionUID = -8159276431356429981L;

    /**
     * id
     */
    private Integer id;

    /**
     * job名称
     */
    private String jobName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 资方
     */
    private Integer capitalSource;

    /**
     * 任务类型
     * @see com.credithc.xd.late.common.enums.JobExecutionTraceTypeEnum
     */
    private Integer jobType;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 状态：处理中1，成功2，失败3
     * @see com.credithc.xd.late.common.enums.JobExecutionTraceStatusEnum
     */
    private Integer status;

    /**
     * 执行时间
     */
    private Date executeAt;

    /**
     * 触发日期
     */
    private Date triggerDate;

    /**
     * 完成时间
     */
    private Date finishedAt;

    /**
     * 失败原因
     */
    private String failedCause;

    /**
     * MySQL 驱动包版本太低无法使用LocalDateTime 比如5.1.22
     */
    private LocalDateTime createdAt;

    /**
     *
     */
    private LocalDateTime updatedAt;

}
