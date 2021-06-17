package com.hsms.mqserver.data;

import com.hsmq.data.message.Message;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 4:06 下午
 */
public class ConsumerQueue {

    private String topic;
    private String tag;
    private MessageStorage messageStorage = new MessageStorage();

    private LinkedBlockingQueue<MessageDurability> messageMappingQueue = new LinkedBlockingQueue<>();

    public List<Message> pullMessage(int size){
        List<MessageDurability> data = new ArrayList<>();
        for(int i=0;i<size;size++){
            MessageDurability messageDurability = messageMappingQueue.poll();
            if (messageDurability==null){
                break;
            }
            data.add(messageDurability);
        }
        if (data.size()==0){
            return null;
        }

        return messageStorage.readMessages(data);
    }

    public void addMessage(MessageDurability messageDurability){
//        if (isAdd(messageDurability)){
//        }
        messageMappingQueue.add(messageDurability);
    }

    private boolean isAdd(MessageDurability messageDurability){
        return tag.equals(messageDurability.getTags());
    }

}
