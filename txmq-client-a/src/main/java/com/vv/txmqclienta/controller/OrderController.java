package com.vv.txmqclienta.controller;

import com.vv.txmqclienta.TransactionProducer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    TransactionProducer producer;

    //    @PostMapping
    @GetMapping("/")
    public String order(String productId) throws MQClientException {
        producer.send("create_order", productId);
        return "success";
    }
}
