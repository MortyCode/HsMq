package com.hsmq.data.message;

import java.io.Serializable;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 3:05 下午
 */
public class TopicData implements Serializable {

    private static final long serialVersionUID = -20210610L;

    private String consumerName;
    private String topic;

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
