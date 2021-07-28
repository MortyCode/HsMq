package com.hsms.mqserver.data;

import com.hsmq.data.message.SendMessage;
import com.hsmq.data.message.Pull;
import com.hsmq.storage.durability.MessageDurability;
import com.hsms.mqserver.storage.MessageListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 5:20 下午
 */
public class ConsumerQueueManger {

    private static ConcurrentMap<String, MessageListener> data = new ConcurrentHashMap<>();

    public static void registerTopic(String topic){
        data.put(topic, new ConcurrentHashMap<>(16));
    }

    public static void recoveryConsumer(){

    }

    public static ConsumerQueue getAndRegister(Pull pull){
        ConcurrentMap<String,ConsumerQueue> queue;
        if ((queue=data.get(pull.getTopic()))==null){
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

    public static boolean existsTopic(SendMessage sendMessage){
        return data.get(sendMessage.getTopic())!=null;
    }

    public static boolean pushConsumerQueue(SendMessage sendMessage, MessageDurability messageDurability){
        ConcurrentMap<String, ConsumerQueue> consumerQueueMap = data.get(sendMessage.getTopic());
        if (consumerQueueMap==null){
           return false;
        }

        for (Map.Entry<String, ConsumerQueue> entry : consumerQueueMap.entrySet()) {
            entry.getValue().addMessage(messageDurability);
        }
        return true;
    }

}
