package ai.lifo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author zhanglifeng
 * @since 2024-06-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Integer id;

    private String name;

    private LocalDateTime eventTime;
}
