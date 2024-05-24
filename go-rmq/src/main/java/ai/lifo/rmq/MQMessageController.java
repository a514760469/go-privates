package ai.lifo.rmq;

import ai.lifo.rmq.producer.AsyncSendCallback;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglifeng
 * @since 2024-02-18
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MQMessageController implements InitializingBean {

    private final RocketMQTemplate rocketMQTemplate;

    private static final String SYNC_TAG = "t_topic_1:syncTag";

    private static final String ASYNC_TAG = "t_topic_1:asyncTag";

    private static final String ONEWAY_TAG = "t_topic_1:onewayTag";

    @PostMapping("/push")
    public String push(@RequestParam int id) {
        rocketMQTemplate.convertAndSend("t_topic_1", "hello world" + id);
        return "connected";
    }

    /**
     * rocketmq 同步消息
     *
     * @param id 消息
     * @return 结果
     */
    @PostMapping("/pushMessage/sync")
    public ResponseMsg pushMessage(@RequestParam("id") int id) {
        log.info("pushMessage start : " + id);
        // 构建消息
        String messageStr = "order id : " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();
        // 设置发送地和消息信息并发送同步消息
        SendResult sendResult = rocketMQTemplate.syncSend(SYNC_TAG, message);
        log.info("pushMessage finish : " + id + ", sendResult : " + JSONObject.toJSONString(sendResult));
        ResponseMsg msg = new ResponseMsg();
        // 解析发送结果
        if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
            msg.setSuccessData(sendResult.getMsgId() + " : " + sendResult.getSendStatus());
        }
        return msg;
    }

    /**
     * rocketmq 异步消息
     *
     * @param id 消息
     * @return 结果
     */
    @PostMapping("/pushMessage/async")
    public ResponseMsg pushMessageAsync(@RequestParam("id") int id) {
        log.info("pushAsyncMessage start : " + id);
        // 构建消息
        String messageStr = "order id : " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();
        // 设置发送地和消息信息并发送异步消息
        rocketMQTemplate.asyncSend(ASYNC_TAG, message, new AsyncSendCallback(id));

        log.info("pushAsyncMessage finish: " + id);
        ResponseMsg msg = new ResponseMsg();
        msg.setSuccessData(null);
        return msg;
    }

    /**
     * 发送单向消息（不关注发送结果：记录日志）
     *
     * @param id 消息
     * @return 结果
     */
    @RequestMapping("/pushMessage/oneway")
    public ResponseMsg pushOneWayMessage(@RequestParam("id") int id) {
        log.info("pushOneWayMessage start: " + id);
        // 构建消息
        String messageStr = "order id: " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();

        // 设置发送地和消息信息并发送单向消息
        rocketMQTemplate.sendOneWay(ONEWAY_TAG, message);
        log.info("pushOneWayMessage finish: " + id);
        ResponseMsg msg = new ResponseMsg();
        msg.setSuccessData(null);
        return msg;
    }

    /**
     * 发送包含顺序的单向消息
     *
     * @param id 消息
     * @return 结果
     */
    @RequestMapping("/pushMessage/seq")
    public ResponseMsg pushSequenceMessage(@RequestParam("id") int id) {
        log.info("pushSequenceMessage start: " + id);
        // 创建三个不同订单的不同步骤
        for (int i = 0; i < 3; i++) {
            // 处理当前订单唯一标识
            String myId = id + "" + i;
            // 获取当前订单的操作步骤列表
            List<OrderStep> myOrderSteps = OrderStep.buildOrderSteps(myId);
            // 依次操作步骤下发消息队列
            for (OrderStep orderStep : myOrderSteps) {
                // 构建消息
                String messageStr = String.format("order id : %s, desc : %s", orderStep.getId(), orderStep.getDesc());
                Message<String> message = MessageBuilder.withPayload(messageStr)
                        .setHeader(RocketMQHeaders.KEYS, orderStep.getId())
                        .build();

                // 设置发送地和消息信息并发送消息（Orderly）
                rocketMQTemplate.syncSendOrderly(SYNC_TAG, message, orderStep.getId());
            }
        }
        log.info("pushSequenceMessage finish: " + id);
        ResponseMsg msg = new ResponseMsg();
        msg.setSuccessData(null);
        return msg;
    }

    /**
     * rocketmq 延迟消息
     *
     * @param id 消息
     * @return 结果
     */
    @RequestMapping("/pushMessage/delay")
    public ResponseMsg pushDelayMessage(@RequestParam("id") int id) {
        log.info("pushDelayMessage start: " + id);
        // 构建消息
        String messageStr = "order id: " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();
        // 设置超时和延时推送
        // 超时时针对请求broker然后结果返回给product的耗时
        // 现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，从1s到2h分别对应着等级1到18
        // private String messageDelayLevel = "1s 5s 10s 30 s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
        SendResult sendResult = rocketMQTemplate.syncSend(SYNC_TAG, message, 1000L, 4);
//        SendResult sendResult = rocketMQTemplate.syncSendDelayTimeSeconds(syncTag, message, 60L);

        log.info("pushDelayMessage finish: " + id + ", sendResult : " + JSONObject.toJSONString(sendResult));
        ResponseMsg msg = new ResponseMsg();
        // 解析发送结果
        if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
            msg.setSuccessData(sendResult.getMsgId() + " : " + sendResult.getSendStatus());
        }
        return msg;
    }

    /**
     * rocketmq 定时消息 (5.0 以上broker才支持)
     *
     * @param id 消息
     * @return 结果
     */
    @RequestMapping("/pushMessage/timed")
    public ResponseMsg pushTimedMessage(@RequestParam("id") int id) {
        log.info("pushTimedMessage start: " + id);
        // 构建消息
        String messageStr = "order id: " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .build();
        // private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
        SendResult sendResult = rocketMQTemplate.syncSendDelayTimeSeconds(SYNC_TAG, message, 30L);
        log.info("pushTimedMessage finish: " + id + ", sendResult : " + JSONObject.toJSONString(sendResult));

        ResponseMsg msg = new ResponseMsg();
        // 解析发送结果
        if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
            msg.setSuccessData(sendResult.getMsgId() + " : " + sendResult.getSendStatus());
        }
        return msg;
    }

    /**
     * 同时发送10个单向消息（真正的批量）
     *
     * @param id 消息
     * @return 结果
     */
    @RequestMapping("/pushMessage/batch")
    public ResponseMsg pushBatchMessage(@RequestParam("id") int id) {
        log.info("pushBatchMessage start: " + id);
        // 创建消息集合
        List<Message<String>> messages = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String myId = id + "" + i;
            // 处理当前订单唯一标识
            String messageStr = "order id: " + myId;
            Message<String> message = MessageBuilder.withPayload(messageStr)
                    .setHeader(RocketMQHeaders.KEYS, myId)
                    .build();
            messages.add(message);
        }
        // 批量下发消息到broker,不支持消息顺序操作，并且对消息体有大小限制（不超过4M）
        ListSplitter<Message<String>> splitter = new ListSplitter<>(messages, 1024 * 1024 * 4);
        while (splitter.hasNext()) {
            List<Message<String>> listItem = splitter.next();
            rocketMQTemplate.syncSend(SYNC_TAG, listItem);
        }
        log.info("pushBatchMessage finish: " + id);
        ResponseMsg msg = new ResponseMsg();
        msg.setSuccessData(null);
        return msg;
    }

    /**
     * sql过滤消息
     *
     * @param id 消息
     * @return 结果
     */
    @RequestMapping("/pushMessage/sql")
    public ResponseMsg pushSqlMessage(@RequestParam("id") int id) {
        log.info("pushSqlMessage start: " + id);
        // 创建消息集合
        List<Message<String>> messages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String myId = id + "" + i;
            // 处理当前订单唯一标识
            String messageStr = "order id: " + myId;
            Message<String> message = MessageBuilder.withPayload(messageStr)
                    .setHeader(RocketMQHeaders.KEYS, myId)
                    .setHeader("money", i)
                    .build();
            messages.add(message);
        }
        rocketMQTemplate.syncSend(SYNC_TAG, messages);
        log.info("pushSqlMessage finish: " + id);
        ResponseMsg msg = new ResponseMsg();
        msg.setSuccessData(null);
        return msg;
    }

    /**
     * 事务消息
     *
     * @param id 消息
     * @return 结果
     */
    @RequestMapping("/pushMessage/transaction")
    public ResponseMsg pushTransactionMessage(@RequestParam("id") int id) {
        log.info("pushTransactionMessage start : " + id);
        // 创建消息
        String messageStr = "order id: " + id;
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, id)
                .setHeader("money", 10)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, id)
                .build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(SYNC_TAG, message, null);
        log.info("pushTransactionMessage result: " + JSONObject.toJSONString(transactionSendResult));
        log.info("pushTransactionMessage finish: " + id);
        ResponseMsg msg = new ResponseMsg();
        msg.setSuccessData(null);
        return msg;
    }

    @Override
    public void afterPropertiesSet() {
        // 设置顺序下发
        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            /**
             * 设置放入同一个队列的规则
             * @param list 消息列表
             * @param message 当前消息
             * @param o 比较的关键信息
             * @return 消息队列
             */
            @Override
            public MessageQueue select(List<MessageQueue> list, org.apache.rocketmq.common.message.Message message, Object o) {
                // 根据当前消息的id，使用固定算法获取需要下发的队列
                // （使用当前id和消息队列个数进行取模获取需要下发的队列，id和队列数量一样时，选择的队列坑肯定一样）
                int queueNum = Integer.parseInt(String.valueOf(o)) % list.size();
                log.info(String.format("queueNum: %s, message: %s", queueNum, new String(message.getBody())));
                return list.get(queueNum);
            }
        });
    }
}
