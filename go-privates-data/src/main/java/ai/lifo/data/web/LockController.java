package ai.lifo.data.web;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhanglifeng
 * @since 2024-05-31
 */
@RestController
public class LockController {

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("test-lock")
    public String TestLock() throws InterruptedException {
        // 1.获取锁，只要锁的名字一样，获取到的锁就是同一把锁。
        RLock lock = redissonClient.getLock("WuKong-lock");

        // 2.加锁
//        lock.lock();
        if (lock.tryLock(3, TimeUnit.SECONDS)) {

            try {
                System.out.println("加锁成功，执行后续代码。线程 ID：" + Thread.currentThread().getId());
                Thread.sleep(40000);
            } catch (Exception e) {
                //TODO
            } finally {
                lock.unlock();
                // 3.解锁
                System.out.println("Finally，释放锁成功。线程 ID：" + Thread.currentThread().getId());
            }
        }

        return "test lock ok";
    }

}
