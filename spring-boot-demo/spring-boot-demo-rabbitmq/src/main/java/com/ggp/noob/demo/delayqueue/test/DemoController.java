package com.ggp.noob.demo.delayqueue.test;

import com.ggp.noob.demo.delayqueue.DelayedSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author Created by gongguanpeng on 2022/2/22 16:56
 */
@RestController
public class DemoController {
    @Autowired
    private DelayedSender delayedSender;

    @Autowired
    private Worker worker;

    /**
     * 测试发送延时消息
     *
     * @return
     */
    @GetMapping("/demo/delayedSender")
    public String delayedSender() throws Exception{
        Person person = new Person();
        person.setName("ggp");
        person.setAge(18);
        delayedSender.delayedMessage(TimeUnit.SECONDS,2,person,worker::workObject);
        return "scs send ok";
    }
}
