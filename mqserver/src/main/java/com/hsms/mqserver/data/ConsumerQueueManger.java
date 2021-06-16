package com.hsms.mqserver.data;

import com.hsmq.data.message.Message;
import com.hsmq.data.message.Pull;
import com.hsmq.storage.durability.MessageDurability;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 5:20 下午
 */
public class ConsumerQueueManger {

    private static ConcurrentMap<String, ConcurrentMap<String,ConsumerQueue>> data =
            new ConcurrentHashMap<>();


    public ConsumerQueue getAndRegister(Pull pull){

        ConcurrentMap<String,ConsumerQueue> queue;
        if ((queue=data.get(pull.getTopic()))==null){
            queue = new ConcurrentHashMap<>();
            data.put(pull.getTopic(),queue);
            return null;
        }

        ConsumerQueue consumerQueue;
        if ((consumerQueue=queue.get(pull.getConsumerName()))==null){
            consumerQueue = new ConsumerQueue();
            queue.put(pull.getConsumerName(),consumerQueue);
            return null;
        }

        return consumerQueue;
    }

    public void pushConsumerQueue(Message message,MessageDurability messageDurability){
        ConcurrentMap<String, ConsumerQueue> consumerQueueMap = data.get(message.getTopic());
        if (consumerQueueMap==null){
            synchronized (this){
                consumerQueueMap = data.get(message.getTopic());
                if (consumerQueueMap==null){
                    consumerQueueMap = new ConcurrentHashMap<>();
                    data.put(message.getTopic(),consumerQueueMap);
                }
            }
        }
        for (Map.Entry<String, ConsumerQueue> entry : consumerQueueMap.entrySet()) {
            entry.getValue().addMessage(messageDurability);
        }
    }

}
