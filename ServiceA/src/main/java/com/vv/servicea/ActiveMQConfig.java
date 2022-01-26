package com.vv.servicea;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@Configuration
public class ActiveMQConfig {
    @Value("${spring.activemq.broker-url}")
    private String address;

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("ServerALocalEventQueue");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory(address);
    }
}
