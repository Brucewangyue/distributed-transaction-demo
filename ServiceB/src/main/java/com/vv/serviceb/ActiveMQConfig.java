package com.vv.serviceb;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@Configuration
public class ActiveMQConfig {
    @Value("${spring.activemq.broker-url}")
    private String address;

    /**
     * 重发策略：客户端不ack，下次会再次消费
     * @return
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        return new RedeliveryPolicy();
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(RedeliveryPolicy redeliveryPolicy) {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(address);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory jmsListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(activeMQConnectionFactory);
        // 1自动确认，2手动确认，3自动批量确认，4事务提交并确认
        defaultJmsListenerContainerFactory.setSessionAcknowledgeMode(2);
        return defaultJmsListenerContainerFactory;
    }
}
