package com.hsmq.data;

import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.ResultEnum;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 3:05 下午
 */
public class Message implements Serializable {

    private String msgId;
    private String topic;
    private String tag;
    private String body;
    /**
     * @see MessageEnum
     */
    private String type;
    /**
     * @see ResultEnum
     */
    private String result;


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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Message.class.getSimpleName() + "[", "]")
                .add("msgId='" + msgId + "'")
                .add("topic='" + topic + "'")
                .add("tag='" + tag + "'")
                .add("body='" + body + "'")
                .add("type='" + type + "'")
                .add("result='" + result + "'")
                .toString();
    }
}
