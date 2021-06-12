package com.hsms.mqserver.data;

import com.hsmq.data.Message;
import com.hsmq.data.Pull;
import com.hsmq.enums.ResultEnum;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:10 下午
 */
public class MessageStore {

    private static InternalLogger logger = InternalLoggerFactory.getInstance(MessageStorage.class);
//    private static ConcurrentHashMap<String,ConcurrentLinkedQueue<Message>> data = new ConcurrentHashMap<>();


    private static ConcurrentMap<String,ConcurrentMap<String,ConsumerQueue>> data =
            new ConcurrentHashMap<>();


    private MessageStorage messageStorage = new MessageStorage();

    public Message pullMessage(Pull pull){
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

        return consumerQueue.pullMessage();
    }

    public String save(Message message){

        String msgId = UUID.randomUUID().toString();
        message.setMsgId(msgId);

        MessageDurability messageDurability = messageStorage.saveMessage(message);
        messageDurability.setTags(message.getTag());
        logger.info("messageDurability:{}",messageDurability);


        ConcurrentMap<String, ConsumerQueue> consumerQueueMap = data.get(message.getTopic());
        if (consumerQueueMap==null){
            synchronized (this){
                consumerQueueMap = data.get(message.getTopic());
                if (consumerQueueMap==null){
                    consumerQueueMap = new ConcurrentHashMap<>();
                }
            }
        }

        for (Map.Entry<String, ConsumerQueue> entry : consumerQueueMap.entrySet()) {
            entry.getValue().addMessage(messageDurability);
        }

        return msgId;
    }

}
