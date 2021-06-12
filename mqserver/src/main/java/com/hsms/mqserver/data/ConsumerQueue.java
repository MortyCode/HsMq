package com.hsms.mqserver.data;

import com.hsmq.data.Message;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 4:06 下午
 */
public class ConsumerQueue {

    private String topic;
    private String tag;
    private MessageStorage messageStorage;

    private ConcurrentLinkedQueue<MessageDurability> messageMappingQueue;




    public Message pullMessage(){
        MessageDurability messageDurability = messageMappingQueue.poll();
        if (messageDurability==null){
            return null;
        }
        return messageStorage.readMessage(messageDurability);
    }

    public void addMessage(MessageDurability messageDurability){
        if (isAdd(messageDurability)){
            messageMappingQueue.add(messageDurability);
        }

    }

    private boolean isAdd(MessageDurability messageDurability){
        return tag.equals(messageDurability.getTags());
    }

}
