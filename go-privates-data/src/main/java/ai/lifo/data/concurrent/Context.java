package ai.lifo.data.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 描 述: 生产、消费线程上下文
 *
 * @author zhanglifeng
 */
@Slf4j
public class Context<E> {

    private final LinkedBlockingQueue<E> consumptionQueue = new LinkedBlockingQueue<>(2500);

    /**
     * 生产线程状态
     */
    private volatile ThreadState producersThreadState;

    /**
     * 消费线程状态
     */
    private volatile ThreadState consumersThreadState;

    /**
     * 描 述：获取队列大小
     */
    int getConsumptionQueueSize() {
        return consumptionQueue.size();
    }

    /**
     * 描 述：将指定元素插入到此队列的尾部，如有必要（队列空间已满且消费线程未停止运行），则等待空间变得可用。
     *
     * @param e element
     * @return boolean true:插入成功;false:插入失败（消费线程已停止运行）
     * @throws InterruptedException InterruptedException
     */
    public boolean offerDataToConsumptionQueue(E e) throws InterruptedException {
        this.producersThreadState = ThreadState.RUNNING;
        // 如果消费线程停止了，不再生产数据
        if (ThreadState.DEAD == this.consumersThreadState) {
            return false;
        }
        while (true) {
            // 插入元素，等待最多2秒到空间可用
            if (consumptionQueue.offer(e, 2, TimeUnit.SECONDS)) {
                return true;
            }
            // 添加元素失败，很有可能是队列已满，再次检查消费线程是否工作中
            if (ThreadState.DEAD == this.consumersThreadState) {
                // 如果消费线程停止了，不再生产数据
                return false;
            }
        }
    }

    /**
     * 描 述：获取并移除此队列的头，如果此队列为空且生产线程已停止，则返回 null
     *
     * @return E 队列的头元素，如果队列为空且生产线程已停止则返回null
     * @throws InterruptedException InterruptedException
     */
    public E pollDataFromConsumptionQueue() throws InterruptedException {
        this.consumersThreadState = ThreadState.RUNNING;
        while (true) {
            E e = consumptionQueue.poll(20, TimeUnit.MILLISECONDS);
            if (e != null) {
                return e;
            }
            // 没有从队列里获取到元素，并且生产线程已停止，则返回null
            if (ThreadState.DEAD == this.producersThreadState) {
                return null;
            }
            log.debug("demand exceeds supply(供不应求，需生产数据)...");
            TimeUnit.MILLISECONDS.sleep(50);
        }
    }

    /**
     * 描 述：获取并移除此队列的头部，在元素变得可用之前一直等待（如果有必要）。 <br>
     * 使用take方法避免忙等待
     * @return E
     * @throws InterruptedException InterruptedException
     */
    public E takeDataFromConsumptionQueue() throws InterruptedException {
        return consumptionQueue.take();
    }


    /**
     * 获取 producersThreadState
     *
     * @return producersThreadState
     */
    ThreadState getProducersThreadState() {
        return this.producersThreadState;
    }

    /**
     * 设置 producersThreadState
     *
     * @param producersThreadState state
     */
    void setProducersThreadState(ThreadState producersThreadState) {
        this.producersThreadState = producersThreadState;
    }

    /**
     * 获取 consumersThreadState
     *
     * @return consumersThreadState
     */
    ThreadState getConsumersThreadState() {
        return consumersThreadState;
    }

    /**
     * 设置 consumersThreadState
     *
     * @param consumersThreadState state
     */
    void setConsumersThreadState(ThreadState consumersThreadState) {
        this.consumersThreadState = consumersThreadState;
    }

}
