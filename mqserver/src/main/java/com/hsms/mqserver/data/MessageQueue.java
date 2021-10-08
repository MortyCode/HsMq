package com.hsms.mqserver.data;

import com.hsmq.data.message.Pull;
import com.hsmq.data.message.PullMessage;
import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.data.MessageDurabilityStorage;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ：河神
 * @date ：Created in 2021/10/1 10:37 上午
 */
public class MessageQueue {

    private final ConcurrentLinkedQueue<MessageDurability> messageQueue;


    public MessageQueue(List<MessageDurability> data) {
        this.messageQueue = new ConcurrentLinkedQueue<>();
        if (data!=null&&data.size()>0){
            this.messageQueue.addAll(data);
        }
    }

    public List<PullMessage> pullMessage(Pull pull){
        int size = pull.getSize();
        List<MessageDurability> data = new ArrayList<>();
        for(int i=0;i<size;size++){
            MessageDurability messageDurability = messageQueue.poll();
            if (messageDurability==null){
                break;
            }
            data.add(messageDurability);
        }

        MessageDurabilityStorage.readMessageQueue(pull.getQueueId(),pull.getTopic(),pull.getOffset());

        if (data.size()==0){
            return null;
        }

        return MessageStorage.readMessages(data);
    }

    public void addMessage(MessageDurability messageDurability){
        messageQueue.add(messageDurability);
    }




}
