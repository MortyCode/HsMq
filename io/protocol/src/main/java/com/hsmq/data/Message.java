package com.hsmq.data;

import java.io.Serializable;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 3:05 下午
 */
public class Message implements Serializable {

    private String msgId;
    private String topic;
    private String tag;
    private String key;
    private String body;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
