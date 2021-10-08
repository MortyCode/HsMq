package com.hsmq.data.message;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 3:05 下午
 */
public class PullMessage implements Serializable {

    private static final long serialVersionUID = -20210610L;

    private String msgId;
    private String topic;
    private String tag;
    private String key;
    private String body;
    private Long index;



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

    @Override
    public String toString() {
        return new StringJoiner(", ", PullMessage.class.getSimpleName() + "[", "]")
                .add("msgId='" + msgId + "'")
                .add("topic='" + topic + "'")
                .add("tag='" + tag + "'")
                .add("key='" + key + "'")
                .add("body='" + body + "'")
                .add("index=" + index)
                .toString();
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

}
