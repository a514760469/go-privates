package ai.lifo.rmq.producer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

/**
 * @author zhanglifeng
 * @since 2024-02-18
 */
@Slf4j
public class AsyncSendCallback implements SendCallback {

    private final int id;

    public AsyncSendCallback(int id) {
        this.id = id;
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        log.info("CallBackListener on success id: " + id);
        log.info("CallBackListener on success: " + JSONObject.toJSONString(sendResult));
    }

    @Override
    public void onException(Throwable e) {
        log.error("CallBackListener on exception : ", e);
    }
}
