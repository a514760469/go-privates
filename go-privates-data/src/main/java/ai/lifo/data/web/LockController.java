package ai.lifo.data.web;

import ai.lifo.data.redis.BusinessService;
import ai.lifo.entity.Event;
import ai.lifo.entity.NestedGeneric;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhanglifeng
 * @since 2024-05-31
 */
@RestController
public class LockController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BusinessService businessService;

    @GetMapping("test-lock")
    public String testLock() throws InterruptedException {
        // 1.获取锁，只要锁的名字一样，获取到的锁就是同一把锁。
        RLock lock = redissonClient.getLock("test-lock");

        // 2.加锁
//        lock.lock();
        if (lock.tryLock(3, TimeUnit.SECONDS)) {

            try {
                System.out.println("加锁成功，执行后续代码。线程 ID：" + Thread.currentThread().getId());
                Thread.sleep(40000);
            } catch (Exception e) {
                //
            } finally {
                lock.unlock();
                // 3.解锁
                System.out.println("Finally，释放锁成功。线程 ID：" + Thread.currentThread().getId());
            }
        }

        return "test lock ok";
    }

    @GetMapping("cacheable")
    public List<Event> testRedissonCache() {
        return businessService.getEventList("whatever");
    }

    @GetMapping("cacheable2")
    public List<NestedGeneric<Event>> testRedissonCache2() {
        return businessService.getNestedGenerics(new Event(1, "2", LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0))));
    }

    static LocalCachedMapOptions<Object, Object> options = LocalCachedMapOptions.defaults()
            // 用于淘汰清除本地缓存内的元素
            // 共有以下几种选择:
            // LFU - 统计元素的使用频率，淘汰用得最少（最不常用）的。
            // LRU - 按元素使用时间排序比较，淘汰最早（最久远）的。
            // SOFT - 元素用Java的WeakReference来保存，缓存元素通过GC过程清除。
            // WEAK - 元素用Java的SoftReference来保存, 缓存元素通过GC过程清除。
            // NONE - 永不淘汰清除缓存元素。
            .evictionPolicy(LocalCachedMapOptions.EvictionPolicy.NONE)
            // 如果缓存容量值为0表示不限制本地缓存容量大小
            .cacheSize(1000)
            // 以下选项适用于断线原因造成了未收到本地缓存更新消息的情况。
            // 断线重连的策略有以下几种：
            // CLEAR - 如果断线一段时间以后则在重新建立连接以后清空本地缓存
            // LOAD - 在服务端保存一份10分钟的作废日志
            //        如果10分钟内重新建立连接，则按照作废日志内的记录清空本地缓存的元素
            //        如果断线时间超过了这个时间，则将清空本地缓存中所有的内容
            // NONE - 默认值。断线重连时不做处理。
            .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.NONE)
            // 以下选项适用于不同本地缓存之间相互保持同步的情况
            // 缓存同步策略有以下几种：
            // INVALIDATE - 默认值。当本地缓存映射的某条元素发生变动时，同时驱逐所有相同本地缓存映射内的该元素
            // UPDATE - 当本地缓存映射的某条元素发生变动时，同时更新所有相同本地缓存映射内的该元素
            // NONE - 不做任何同步处理
            .syncStrategy(LocalCachedMapOptions.SyncStrategy.INVALIDATE)
            // 每个Map本地缓存里元素的有效时间，默认毫秒为单位
            .timeToLive(10000)
            // 或者
            .timeToLive(10, TimeUnit.SECONDS)
            // 每个Map本地缓存里元素的最长闲置时间，默认毫秒为单位
            .maxIdle(10000)
            // 或者
            .maxIdle(10, TimeUnit.SECONDS);

    @GetMapping("redisson")
    public Object testRedissonClient(@RequestParam String name) {
        RLocalCachedMap<Object, Object> map = redissonClient.getLocalCachedMap("test", LocalCachedMapOptions.defaults().cacheSize(1));
        return map.put(name, name);
    }
}
