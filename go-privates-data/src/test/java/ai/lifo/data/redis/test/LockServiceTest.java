package ai.lifo.data.redis.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhanglifeng
 * @since 2024-05-30
 */
@SpringBootTest
class LockServiceTest {

    @Autowired
    LockService lockService;

    @Test
    void necessaryLock() {

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lockService.necessaryLock();
                System.out.println("count: " + lockService.count);
            }).start();
        }
    }

    @Test
    void necessaryLock1() {

        lockService.necessaryLock();
    }
}