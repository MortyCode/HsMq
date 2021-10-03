package com.hsmq.data.message;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/10/2 3:00 下午
 */
public class PullMessageResp implements Serializable {
    private static final long serialVersionUID = 6307404206182632758L;

    private List<PullMessage> pullMessages;

    private String topic;

    private Integer queueId;

    private Long lastOffset;

    public List<PullMessage> getPullMessages() {
        return pullMessages;
    }

    public void setPullMessages(List<PullMessage> pullMessages) {
        this.pullMessages = pullMessages;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public Long getLastOffset() {
        return lastOffset;
    }

    public void setLastOffset(Long lastOffset) {
        this.lastOffset = lastOffset;
    }
}
