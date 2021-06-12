package com.hsmq.data;

import java.io.Serializable;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 3:05 下午
 */
public class Pull implements Serializable {

    private static final long serialVersionUID = -20210610L;

    private String consumerName;

    private String topic;
    private String tags;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
