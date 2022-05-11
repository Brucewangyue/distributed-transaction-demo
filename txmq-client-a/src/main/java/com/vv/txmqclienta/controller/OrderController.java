package com.vv.txmqclienta.controller;

import com.vv.txmqclienta.OrderProducer;
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

    @Autowired
    OrderProducer orderProducer;

    //    @PostMapping
    @GetMapping("/")
    public String order(String productId) throws MQClientException {
        producer.send("create_order", productId);
        return "success";
    }

    @GetMapping("send")
    public String send(String msg,String tag,String key) throws Exception {
        orderProducer.send((long)123,msg,tag,key);
        return "success";
    }
}
