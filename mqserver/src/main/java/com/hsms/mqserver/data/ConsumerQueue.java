package com.hsms.mqserver.data;

import com.hsmq.data.message.Pull;
import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 4:06 下午
 */
public class ConsumerQueue {

    private MessageStorage messageStorage = new MessageStorage();

    private ConcurrentLinkedQueue<MessageDurability> messageMappingQueue = new ConcurrentLinkedQueue<>();

    public List<SendMessage> pullMessage(Pull pull){
        int size = pull.getSize();
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
        messageMappingQueue.add(messageDurability);
    }
}
