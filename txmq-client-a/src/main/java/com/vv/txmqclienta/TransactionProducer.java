package com.vv.txmqclienta;

import com.vv.txmqclienta.service.OrderService;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Configuration
public class TransactionProducer {
    private String group = "vv_order";

    private TransactionMQProducer producer;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(50), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName("client-transaction-msg-check-thread");
            return thread;
        }
    });

    @Autowired
    OrderService orderService;

    @PostConstruct
    public void init() {
        producer = new TransactionMQProducer(group);
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(Integer.MAX_VALUE);
        producer.setExecutorService(executor);
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 发送 half message 返回 ok 后调用
             * @param message
             * @param o
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                // 根据消息的topic调用本地业务
                // 假如是订单业务
                LocalTransactionState state;

                try {
                    // todo 这部分业务要抽离 - 反射 - 根据topic反射业务类
                    String topic = message.getTopic();
                    String body = new String(message.getBody());
//                    Order order = JSONObject.parseObject(body, Order.class);
                    orderService.order(body);

                    // 成功-提交
                    state = LocalTransactionState.COMMIT_MESSAGE;
                } catch (Exception e) {
                    // 失败-回滚
                    state = LocalTransactionState.ROLLBACK_MESSAGE;
                }

                return state;
            }

            /**
             * 通知rocketmq状态失败后，rocketmq定时任务回查方法
             * @param messageExt
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                // todo 回查多次失败人工补偿
                LocalTransactionState state;
                try {
                    String transactionId = messageExt.getTransactionId();
                    // 去本地事务表查询事务状态
                    if (true) {
                        state = LocalTransactionState.COMMIT_MESSAGE;
                    } else {
                        // 返回未知状态，定时任务继续反查指定次数
                        state = LocalTransactionState.UNKNOW;
                    }
                } catch (Exception e) {
                    state = LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return state;
            }
        });

        start();
    }

    private void start() {
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public TransactionSendResult send(String topic, String data) throws MQClientException {
        Message message = new Message(topic, data.getBytes(StandardCharsets.UTF_8));
        return producer.sendMessageInTransaction(message, null);
    }
}
