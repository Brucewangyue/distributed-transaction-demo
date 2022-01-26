package com.vv.servicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
@EnableScheduling
@EnableJms
@Controller
public class ServiceAApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAApplication.class, args);
    }

    @PostMapping
    @Transactional(rollbackFor = {Exception.class})
    public String pay() {
        // todo 处理支付业务，事件表插入一条记录
        return "success";
    }
}
