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

    private static ConsumerQueueManger consumerQueueManger;

    static {
        consumerQueueManger = new ConsumerQueueManger();
    }



    private MessageStorage messageStorage = new MessageStorage();

    public Message pullMessage(Pull pull){
        ConsumerQueue consumerQueue = consumerQueueManger.getAndRegister(pull);
        if (consumerQueue==null){
            return null;
        }
        return consumerQueue.pullMessage();
    }

    public void saveMessage(Message message){
        MessageDurability messageDurability = messageStorage.saveMessage(message);
        messageDurability.setTags(message.getTag());
        logger.info("messageDurability:{}",messageDurability);
        consumerQueueManger.pushConsumerQueue(message,messageDurability);
    }

}
