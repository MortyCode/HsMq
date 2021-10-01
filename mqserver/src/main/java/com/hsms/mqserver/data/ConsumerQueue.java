package com.hsms.mqserver.data;

import com.hsmq.data.message.Pull;
import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.config.TopicConfig;
import com.hsmq.storage.durability.MessageDurability;

import java.util.HashMap;
import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 4:06 下午
 */
public class ConsumerQueue {

    private final HashMap<Integer,MessageQueue> messageMappingQueue = new HashMap<>();
    public volatile   int queueId =  0;
    public  int queueNum ;


    public ConsumerQueue(TopicConfig topicConfig) {
        for (int i=0;i<topicConfig.getMessageQueueSize();i++){
            messageMappingQueue.put(i,new MessageQueue());
        }
        this.queueNum = topicConfig.getMessageQueueSize();
    }

    public List<SendMessage> pullMessage(Pull pull){
        MessageQueue messageQueue = messageMappingQueue.get(pull.getQueueNum());
        if (messageQueue==null){
            throw new RuntimeException("messageQueue is not exists");
        }
        return messageQueue.pullMessage(pull);
    }

    public void addMessage(MessageDurability messageDurability){
        int queueId = getQueueId();
        MessageQueue messageQueue = messageMappingQueue.get(queueId);
        if (messageQueue==null){
            throw new RuntimeException("messageQueue is not exists");
        }

        messageQueue.addMessage(messageDurability);
    }

    public synchronized int  getQueueId(){
        queueId++;
        if (queueId>10000){
            queueId = 0;
        }
        return queueId%queueNum;
    }
}
