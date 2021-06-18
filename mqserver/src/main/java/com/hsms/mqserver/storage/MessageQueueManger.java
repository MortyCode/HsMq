package com.hsms.mqserver.storage;

import com.hsmq.storage.config.TopicConfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 5:20 下午
 */
public class MessageQueueManger {

    private static ConcurrentMap<String, ConcurrentMap<Integer, MessageQueue>> data =
            new ConcurrentHashMap<>();

    /**
     * 初始化ConsumerQueues
     */
    public void initMessageQueue(TopicConfig topicConfig){
        ConcurrentMap<Integer,MessageQueue> messageQueueConcurrentMap = new ConcurrentHashMap<>(16);
        for (int queueId=0;queueId<=topicConfig.getMessageQueueSize() ;queueId++){
            MessageQueue consumerQueue = new MessageQueue(topicConfig.getTopicName(),queueId);
            messageQueueConcurrentMap.put(queueId,consumerQueue);
        }
        data.put(topicConfig.getTopicName(),messageQueueConcurrentMap);
    }

    public void loadConsumerQueues(){

    }

}
