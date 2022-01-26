package com.vv.serviceb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vv.serviceb.dao.LocalEventDao;
import com.vv.serviceb.entity.LocalEvent;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.Queue;
import java.util.List;

//@Component
public class LocalEventScheduleService {

    @Resource
    LocalEventDao localEventDao;

    @Resource
    JmsMessagingTemplate jmsMessagingTemplate;

    @Resource
    Queue queue;

    @Scheduled(cron = "0/5 * * * * ?")
    @Transactional(rollbackFor = {Exception.class})
    public void scheduleReceiveLocalEvent() throws JsonProcessingException {
        System.out.println("定时发送消息");
        List<LocalEvent> unSentList = localEventDao.getUnSentList();
        if (unSentList.size() > 0) {
            System.out.println("查询到数据，发送消息");
        }
        for (LocalEvent localEvent : unSentList) {
            localEventDao.updateStatusToSent(localEvent.getId());
            String payLoad = new ObjectMapper().writeValueAsString(localEvent);
            jmsMessagingTemplate.convertAndSend(queue, payLoad);
        }
    }
}
