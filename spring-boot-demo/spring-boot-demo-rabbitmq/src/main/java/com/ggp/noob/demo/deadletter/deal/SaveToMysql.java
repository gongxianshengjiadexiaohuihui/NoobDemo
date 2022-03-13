package com.ggp.noob.demo.deadletter.deal;

import com.ggp.noob.demo.deadletter.demo.database.MessageDO;
import com.ggp.noob.demo.deadletter.demo.database.MessageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Created by gongguanpeng on 2022/3/7 10:27
 */
@Order(2)
@Component
public class SaveToMysql implements DeadLetterMessageDeal {
    @Autowired
    private MessageDao messageDao;
    @Override
    public void deal(Object message, Map headers) {
        System.out.println("存储死信到数据库");
        MessageDO messageDO = new MessageDO();
        messageDO.setExchange(headers.get("x-first-death-exchange").toString());
        messageDO.setQueue(headers.get("x-first-death-queue").toString());
        messageDO.setMessage(message.toString());
        messageDao.insert(messageDO);
    }
}
