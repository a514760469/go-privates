package ai.lifo.data.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author zhanglifeng
 * @since 2024-08-29
 */
@Getter
@RequiredArgsConstructor
public enum UserSts {

    ACTIVE("active"),

    DEACTIVATED("deactivated"),
    ;

    @EnumValue
    @JsonValue
    private final String name;

}
