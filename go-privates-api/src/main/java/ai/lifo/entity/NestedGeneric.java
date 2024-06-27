package ai.lifo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhanglifeng
 * @since 2024-06-26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NestedGeneric<T> {

    private Integer id;

    private String name;

    private Event event;

    private T data;

}
