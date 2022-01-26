package com.vv.lcnserverb;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.codingapi.txlcn.tc.annotation.TccTransaction;
import com.vv.lcnserverb.dao.PayDao;
import com.vv.lcnserverb.entity.Pay;
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
public class LcnServerBApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcnServerBApplication.class, args);
    }

    @Autowired
    PayDao payDao;

    @GetMapping("lcn-lcn")
    @LcnTransaction
    public String lcn() {
        // 数据库操作
        payDao.update(new Pay(1, 2));
        return "success";
    }

    @GetMapping("lcn-tcc")
    @TccTransaction
    @Transactional
    public String tcc() {
        payDao.update(new Pay(1, 2));
        return "success";
    }

    //tcc tm 事务成功回调
    public void confirmTcc() {
        System.out.println("tcc-confirm:pay");
    }

    //tcc tm 事务失败回调
    public void cancelTcc() {
        System.out.println("tcc-cancel:pay");
    }
}
