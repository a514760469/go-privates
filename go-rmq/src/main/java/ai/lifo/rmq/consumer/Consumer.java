package ai.lifo.rmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author zhanglifeng
 * @since 2024-02-18
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "t_topic_1", consumerGroup = "${spring.application.name}")
public class Consumer implements RocketMQListener<String> {


    @Override
    public void onMessage(String message) {
        log.info("消费：" + message);
    }
}
