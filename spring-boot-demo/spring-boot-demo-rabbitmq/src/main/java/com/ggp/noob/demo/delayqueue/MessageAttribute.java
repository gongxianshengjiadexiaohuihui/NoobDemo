package com.ggp.noob.demo.delayqueue;

import java.time.LocalDateTime;

/**
 * @Author Created by gongguanpeng on 2022/3/1 16:29
 * 消息属性信息
 */
class MessageAttribute {
    /**
     * 消息的发送时间
     */
    private LocalDateTime sendTime;
    /**
     * 消息的接受时间
     */
    private LocalDateTime receiveTime;
    /**
     * 用户指定的延迟时间
     */
    private long delayTime;

    /**
     * 延迟时间到达后，处理消息的回调函数
     */
    private byte[] callback;

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public byte[] getCallback() {
        return callback;
    }

    public void setCallback(byte[] callback) {
        this.callback = callback;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
    }
}
