package com.hsmq.data.message;

import java.io.Serializable;
import java.util.Map;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 3:05 下午
 */
public class SyncOffsetMessage implements Serializable {

    private static final long serialVersionUID = -20210610L;

    private String consumer;
    private String topic;
    private Map<Integer, Long> offSetMap;

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Map<Integer, Long> getOffSetMap() {
        return offSetMap;
    }

    public void setOffSetMap(Map<Integer, Long> offSetMap) {
        this.offSetMap = offSetMap;
    }
}
