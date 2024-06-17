package ai.lifo.data.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试这个生产消费模型
 *
 * @author zhanglifeng
 */
@Service
@Slf4j
public class RemindNoticeHandler implements SimpleTemplate<DataNode>, ConcurrentHandler {

	private final AtomicInteger idCounter = new AtomicInteger(0);

	@Override
	public void run() throws Exception {
		new Coordinator(new Context<>(), 10).start(this);
	}

	@Override
	public void production(Context<DataNode> context) throws Exception {
		log.info("通知信息处理生产开始");

		// 假设总数
		int total = 1020;
		// 每批次最大数量
		int step = 100;
		int current = 0;
		// 模拟生产者生产数据
		while (true) {
			List<DataNode> noticeList = new ArrayList<>();
			for (int i = 0; i < step && current < total; i++) {
				noticeList.add(new DataNode(idCounter.getAndIncrement(), UUID.randomUUID().toString()));
			}
			TimeUnit.SECONDS.sleep(1);// test IO blocking


			if (CollectionUtils.isEmpty(noticeList)) {
				break;
			}
			current = noticeList.get(noticeList.size() - 1).getId();

			for (DataNode data : noticeList) {
				if (!context.offerDataToConsumptionQueue(data)) {
					return;
				}
			}

			log.info("生产了一轮数据: size = {}, 总数 = {}", noticeList.size(), context.getConsumptionQueueSize());


			if (noticeList.size() < step) {
				// 数据构造完成，等下一轮
				break;
			}
		}
		log.info("生产停止");
	}

	@Override
	public void consumption(DataNode dataNode) {
		log.info("dataNode 消费掉： id={}， name={}", dataNode.getId(), dataNode.getName());
	}


	public static void main(String[] args) throws Exception {
		new RemindNoticeHandler().run();
	}
}
