package com.vv.txmqclienta.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Transactional
    public void order(String productId){
        // 订单创建业务处理

        // 最后记录本地事务表
    }
}
