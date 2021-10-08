package com.hsms.mqserver.data;

import com.hsmq.data.message.Pull;
import com.hsmq.data.message.PullMessage;
import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.config.TopicConfig;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 4:06 下午
 */
public class ConsumerQueue {

    private final HashMap<Integer,MessageQueue> messageMappingQueue = new HashMap<>();
    public int queueSize ;


    public ConsumerQueue(TopicConfig topicConfig) {
        List<MessageDurability> messageDurabilities = MessageStorage.readMessageDurability(0L, Integer.MAX_VALUE,topicConfig.getTopicName());

        Map<Integer,List<MessageDurability>> collect = new HashMap<>();
        for (int i=0;i<messageDurabilities.size();i++){
            int num = i % 4;

            List<MessageDurability> data = collect.get(num);
            if (data==null){
                data = new ArrayList<>();
            }
            data.add(messageDurabilities.get(i));
            collect.put(num,data);
        }

        for (int i=0;i<topicConfig.getMessageQueueSize();i++){
            List<MessageDurability> sendMessagesList = collect.get(i);
            messageMappingQueue.put(i,new MessageQueue(sendMessagesList));
        }
        this.queueSize = topicConfig.getMessageQueueSize();
    }

    public List<PullMessage> pullMessage(Pull pull){
        MessageQueue messageQueue = messageMappingQueue.get(pull.getQueueId());
        if (messageQueue==null){
            throw new RuntimeException("messageQueue is not exists");
        }
        return messageQueue.pullMessage(pull);
    }

    public void addMessage(int queueId,MessageDurability messageDurability){
        MessageQueue messageQueue = messageMappingQueue.get(queueId);
        if (messageQueue==null){
            throw new RuntimeException("messageQueue is not exists");
        }

        messageQueue.addMessage(messageDurability);
    }


    public int getQueueSize() {
        return queueSize;
    }
}
