package com.genepoint.chat.domain;

/**
 * @author xiabiao
 * @since 2023-06-08
 */
public class Message {

    /**
     * 发送人
     */
    private String sender;

    /**
     * 消息内容
     */
    private String payload;

    /**
     * 发送时间
     */
    private Long time;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
