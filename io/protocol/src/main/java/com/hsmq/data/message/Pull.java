package com.hsmq.data.message;

import java.io.Serializable;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 3:05 下午
 */
public class Pull implements Serializable {

    private static final long serialVersionUID = -20210610L;

    private String consumerGroup;
    private int queueId;
    private String topic;
    private int size;
    private long offset;
    private String tags;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
