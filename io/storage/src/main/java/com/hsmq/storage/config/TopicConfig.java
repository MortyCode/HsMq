package com.hsmq.storage.config;

/**
 * @author ：河神
 * @date ：Created in 2021/6/18 11:09 上午
 */
public class TopicConfig {

    private String topicName;
    private int messageQueueSize = 4;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getMessageQueueSize() {
        return messageQueueSize;
    }

    public void setMessageQueueSize(int messageQueueSize) {
        this.messageQueueSize = messageQueueSize;
    }
}
