package com.hsms.mqserver.storage;

import java.nio.MappedByteBuffer;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 4:06 下午
 */
public class MessageQueue {

    private MappedByteBuffer mappedByteBuffer;
    private int queueId;

    public MessageQueue(String topicName,int queueId) {
        this.queueId = queueId;
    }

    public void init(){


    }
}
