package ai.lifo.data.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

/**
 * @author zhanglifeng
 * @since 2024-08-28 
 */
@Data
@TableName(value = "sys_user", autoResultMap = true)
public class SysUser {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField
    private String name;

    @TableField
    private Object sts;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private DescData descData;

    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;

    @TableField(value = "created_by")
    private String createdBy;

    @TableField(value = "updated_by")
    private String updatedBy;

    @Version
    @TableField(value = "version")
    private Integer version;

    @TableLogic
    @TableField(value = "deleted")
    private Boolean deleted;

    @Data
    private static class DescData implements Serializable {

        private String name;

        private String desc;
    }
}