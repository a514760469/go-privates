package ai.lifo.data.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author zhanglifeng
 */
@Slf4j
public class RedisReentrantLock {

    private static final String prefixKey = "locking:";

    private final String lockKey;

    private final RLock lock;

    public RedisReentrantLock(RedissonClient redissonClient, String lockId) {
        //        this.redissonClient = redisUtil.getRedissonClient();
        this.lockKey = prefixKey + lockId;
        this.lock = redissonClient.getLock(lockKey);
    }

    public void lock() {
        lock.lock();
    }

    public boolean tryLock(long timeout, TimeUnit unit) {
        try {
            boolean isLockSuccess = lock.tryLock(timeout, unit);
            log.info("{} lock result:{}", this.lockKey, isLockSuccess);
            return isLockSuccess;
        } catch (InterruptedException e) {
            log.error("interrupted", e);
            throw new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void unlock() {
        try {
            if (this.lock.isHeldByCurrentThread()) {
                this.lock.unlock();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public boolean isLocked() {
        return this.lock.isLocked();
    }

    public boolean isLockedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }
}
