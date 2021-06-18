package com.hsms.mqserver.storage;

import com.hsmq.data.message.Message;
import com.hsmq.storage.config.TopicConfig;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

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
