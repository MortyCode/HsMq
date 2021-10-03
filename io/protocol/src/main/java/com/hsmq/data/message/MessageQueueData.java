package com.hsmq.data.message;

import java.io.Serializable;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 3:05 下午
 */
public class MessageQueueData implements Serializable {

    private static final long serialVersionUID = -20210610L;

    private String topic;

    private Integer queueSize;

    private Map<Integer,Long> offSetMap;

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public Map<Integer, Long> getOffSetMap() {
        return offSetMap;
    }

    public void setOffSetMap(Map<Integer, Long> offSetMap) {
        this.offSetMap = offSetMap;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
