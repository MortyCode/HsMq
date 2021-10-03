package com.hsms.mqserver.data;

import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.config.TopicConfig;
import com.hsmq.storage.data.MessageDurabilityStorage;
import com.hsmq.storage.durability.MessageDurability;
import com.hsms.mqserver.data.ConsumerQueue;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author ：河神
 * @date ：Created in 2021/7/24 5:18 下午
 */
public class TopicListener {

    private HashMap<String, ConsumerQueue> messageMappingQueue;
    private TopicConfig topicConfig;
    private volatile int queueId =  0;
    public int queueSize ;


    public TopicListener(TopicConfig topicConfig) {
        this.messageMappingQueue = new HashMap<>();
        this.topicConfig = topicConfig;
        this.queueSize = topicConfig.getMessageQueueSize();
    }

    public ConsumerQueue getConsumerQueue(String name){
        ConsumerQueue consumerQueue = messageMappingQueue.get(name);
        if (consumerQueue!=null){
            return consumerQueue;
        }
        consumerQueue = new ConsumerQueue(topicConfig);
        messageMappingQueue.put(name,consumerQueue);
        return consumerQueue;
    }

    public boolean addMsg2Queue(SendMessage sendMessage,MessageDurability messageDurability){
        int queueId = getQueueId();
        try {
            MessageDurabilityStorage.saveMessageQueue(queueId,sendMessage.getTopic(), Collections.singletonList(messageDurability));
        } catch (IOException e) {
            e.printStackTrace();
        }
        messageMappingQueue.forEach((k,consumerQueue)->{
            consumerQueue.addMessage(queueId,messageDurability);
        });
        return true;
    }

    public synchronized int  getQueueId(){
        queueId++;
        if (queueId>10000){
            queueId = 0;
        }
        return queueId%queueSize;
    }






}
