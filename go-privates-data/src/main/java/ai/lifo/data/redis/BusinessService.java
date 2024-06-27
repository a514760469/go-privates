package ai.lifo.data.redis;

import ai.lifo.entity.Event;
import ai.lifo.entity.NestedGeneric;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglifeng
 * @since 2024-06-25
 */
@Slf4j
@Service
public class BusinessService {

    static List<Event> TEST_DATA = new ArrayList<>();
    static {
        TEST_DATA.add(new Event(1, "test1", LocalDateTime.now()));
        TEST_DATA.add(new Event(2, "test2", LocalDateTime.now()));
        TEST_DATA.add(new Event(3, "test3", LocalDateTime.now()));
        TEST_DATA.add(new Event(4, "test4", LocalDateTime.now()));
        TEST_DATA.add(new Event(5, "test5", LocalDateTime.now()));
    }

    @Cacheable(value = "event")
    public List<Event> getEventList(String name) {
        log.info("没有走缓存！");
        return TEST_DATA;
    }


    @Cacheable(value = "NestedGeneric")
    public List<NestedGeneric<Event>> getNestedGenerics(Event event) {
        List<NestedGeneric<Event>> list = new ArrayList<>();
        list.add(new NestedGeneric<>(1, "test1", TEST_DATA.get(0), TEST_DATA.get(0)));
        list.add(new NestedGeneric<>(2, "test2", TEST_DATA.get(1), TEST_DATA.get(1)));
        list.add(new NestedGeneric<>(3, "test3", TEST_DATA.get(2), TEST_DATA.get(2)));
        return list;
    }
}
