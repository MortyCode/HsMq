package com.hsms.mqserver.data;

import com.hsmq.data.message.Message;
import com.hsmq.data.message.Pull;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;

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

    public List<Message> pullMessage(Pull pull){
        ConsumerQueue consumerQueue = consumerQueueManger.getAndRegister(pull);
        if (consumerQueue==null){
            return null;
        }
        return consumerQueue.pullMessage(pull.getSize());
    }

    public void saveMessage(Message message){
        MessageDurability messageDurability = messageStorage.saveMessage(message);
        messageDurability.setTags(message.getTag());
        logger.info("messageDurability:{}",messageDurability);
        consumerQueueManger.pushConsumerQueue(message,messageDurability);
    }

}
