package com.ggp.noob.demo.delayqueue.base;


import java.time.Duration;

/**
 * @Author Created by gongguanpeng on 2022/3/1 10:15
 */
public class DelayMessage<T> {
    /**
     * 消息的属性
     */
    private MessageAttribute messageAttribute;
    /**
     * 消息实体
     */
    private T body;


    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public MessageAttribute getMessageAttribute() {
        return messageAttribute;
    }

    public void setMessageAttribute(MessageAttribute messageAttribute) {
        this.messageAttribute = messageAttribute;
    }

    public String printAttributeInfo(){
        return "Send message time:"+messageAttribute.getSendTime()+",receive message time:"+messageAttribute.getReceiveTime()+"The user-specified delay time is "+messageAttribute.getDelayTime()+"millis but the actual delay time is "+ Duration.between(messageAttribute.getSendTime(),messageAttribute.getReceiveTime()).toMillis()+"millis";
    }
}
