package com.vv.serviceb.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class LocalEventConsumer {

    @JmsListener(destination = "ServerALocalEventQueue",containerFactory = "jmsListenerContainerFactory")
    public void receive(TextMessage textMessage, Session session) throws JMSException {
        try{
            //todo 本地事务表数据插入
            int a = 1/0;
            // 手动确认
            textMessage.acknowledge();
        }
        catch (Exception e){
            session.recover();
        }
        System.out.println("收到消息："+textMessage.getText());
    }

    // todo 死信队列补偿
    @JmsListener(destination = "DLQ.ServerALocalEventQueue")
    public void receive2(TextMessage textMessage, Session session) throws JMSException {

        System.out.println("死信队列："+textMessage.getText());
    }
}
