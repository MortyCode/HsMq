package com.hsms.mqserver.data;

import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.config.TopicConfig;
import com.hsmq.storage.durability.MessageDurability;
import com.hsms.mqserver.data.ConsumerQueue;

import java.util.HashMap;

/**
 * @author ：河神
 * @date ：Created in 2021/7/24 5:18 下午
 */
public class TopicListener {

    private HashMap<String, ConsumerQueue> messageMappingQueue;
    private TopicConfig topicConfig;

    public TopicListener(TopicConfig topicConfig) {
        this.messageMappingQueue = new HashMap<>();
        this.topicConfig = topicConfig;
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
        messageMappingQueue.forEach((k,consumerQueue)->{
            consumerQueue.addMessage(messageDurability);
        });
        return true;
    }





}
