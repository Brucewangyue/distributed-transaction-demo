package com.vv.lcnservera;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.vv.lcnservera.dao.OrderDao;
import com.vv.lcnservera.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class LcnServerAApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnServerAApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    OrderDao orderDao;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("lcn-lcn")
    @LcnTransaction
    public String lcn() {
        // 调用服务
        String result = restTemplate.getForObject("http://localhost:5011/lcn-lcn", String.class);
        // 数据库操作
        orderDao.update(new Order(1,2));

        return "success";
    }

    @GetMapping("lcn-tcc")
    @TccTransaction
    @Transactional
    public String tcc() {
        // 调用服务

        // todo 处理业务
        String result = restTemplate.getForObject("http://localhost:5011/lcn-lcn", String.class);


        return "success";
    }

    //tcc tm 事务成功回调
    public void confirmTcc() {
        System.out.println("tcc-confirm:order");
    }

    //tcc tm 事务失败回调
    public void cancelTcc() {
        System.out.println("tcc-cancel:order");
    }
}
