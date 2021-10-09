package com.hsms.mqserver.data;

import com.hsmq.data.message.Pull;
import com.hsmq.data.message.PullMessage;
import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.config.TopicConfig;
import com.hsmq.storage.data.MessageDurabilityStorage;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/7/24 5:18 下午
 */
public class TopicListener {

    private TopicConfig topicConfig;
    private volatile int queueId =  0;
    public int queueSize ;

    public TopicListener(TopicConfig topicConfig) {
        this.topicConfig = topicConfig;
        this.queueSize = topicConfig.getMessageQueueSize();
    }


    public List<PullMessage> pullMessage(Pull pull){
        List<MessageDurability> data = MessageDurabilityStorage.readMessageQueue(pull.getQueueId(), pull.getTopic(), pull.getOffset());
        if (data.size()==0){
            return null;
        }
        return MessageStorage.readMessages(data);
    }

    public boolean addMsg2Queue(SendMessage sendMessage,MessageDurability messageDurability){
        int queueId = getQueueId();
        try {
            MessageDurabilityStorage.saveMessageQueue(queueId,sendMessage.getTopic(), Collections.singletonList(messageDurability));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


    public synchronized int  getQueueId(){
        queueId++;
        if (queueId>10000){
            queueId = 0;
        }
        return queueId%queueSize;
    }

    public int getQueueSize() {
        return queueSize;
    }
}
