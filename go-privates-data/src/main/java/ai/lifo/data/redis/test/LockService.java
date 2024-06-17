package ai.lifo.data.redis.test;

import ai.lifo.data.redis.RedisReentrantLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhanglifeng
 * @since 2024-05-30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LockService {

    private final RedissonClient redissonClient;

    public AtomicInteger count = new AtomicInteger();


    public void necessaryLock() {
        RedisReentrantLock lock = new RedisReentrantLock(redissonClient, "necessaryLock");
        try {
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                count.addAndGet(1);
                System.err.println("necessaryLock: count=" + count);
            } else {
                log.error("锁失败");
            }
        } finally {
            lock.unlock();
        }

    }
}
