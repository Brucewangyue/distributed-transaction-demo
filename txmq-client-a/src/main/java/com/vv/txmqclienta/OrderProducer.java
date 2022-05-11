package com.vv.txmqclienta;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class OrderProducer {
    private static final String GROUP = "order";

    private DefaultMQProducer producer;

    @PostConstruct
    public void init() throws MQClientException {
        producer = new DefaultMQProducer(GROUP);
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
    }

    public SendResult send(Long orderId,String jsonBody,String tag,String key) throws Exception {
        // todo 做json处理
        Message message = new Message("Order",tag,key,jsonBody.getBytes(StandardCharsets.UTF_8));
        SendResult sendResult = producer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                Long orderId = (Long) arg;
                long index = orderId % mqs.size();
                System.out.println("mqs.count="+mqs.size()+",index="+index);
                return mqs.get((int)index);
            }
        },orderId);
        return sendResult;
    }
}
