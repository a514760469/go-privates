package ai.lifo.rmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhanglifeng
 * @since 2024-02-18
 */
@Slf4j
@RocketMQTransactionListener
public class TransactionListener implements RocketMQLocalTransactionListener {


    private static final Map<String, RocketMQLocalTransactionState> TRANSACTION_STATE_MAP = new ConcurrentHashMap<>();

    /**
     * 处理本地事务
     *
     * @param message msg
     * @param o       o
     * @return state
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("执行本地事务");
        MessageHeaders headers = message.getHeaders();
        //获取事务ID
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        TRANSACTION_STATE_MAP.put(transactionId, RocketMQLocalTransactionState.UNKNOWN);
        log.info("transactionId is {}", transactionId);
        try {
            Thread.sleep(10 * 1000L);
        } catch (InterruptedException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        RocketMQLocalTransactionState state = RocketMQLocalTransactionState.ROLLBACK;
        if (transactionId != null && Integer.parseInt(transactionId) % 2 == 0) {
            //执行成功，可以提交事务
            state = RocketMQLocalTransactionState.COMMIT;
        }
        log.info("transactionId is {}, state {}", transactionId, state);
        TRANSACTION_STATE_MAP.remove(transactionId);
        return state;
    }


    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        MessageHeaders headers = message.getHeaders();
        // 获取事务ID
        String transactionId = (String) headers.get(RocketMQHeaders.TRANSACTION_ID);
        log.info("检查本地事务,事务ID:{}", transactionId);
        RocketMQLocalTransactionState state = TRANSACTION_STATE_MAP.get(transactionId);
        if (null != state) {
            return state;
        }
        return RocketMQLocalTransactionState.ROLLBACK;
    }
}
