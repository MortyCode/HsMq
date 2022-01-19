package com.hsmq.storage.data;

import com.hsmq.data.message.PullMessage;
import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.config.StorageConfig;
import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.storage.file.FileOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 持久化消息
 * @author ：河神
 * @date ：Created in 2021/6/11 3:12 下午
 */
public class MessageStorage {

    final static Logger log = LoggerFactory.getLogger(MessageStorage.class);

    /**
     * 存储消息
     * @param sendMessage
     * @return
     */
    public static MessageDurability saveMessage(SendMessage sendMessage){
        try {
            synchronized (MessageStorage.class){
                return FileOperation.save(StorageConfig.MessagePath + "mq_1", sendMessage);
            }
        } catch (IOException | InterruptedException e) {
            log.error("save filer error",e);
        }
        return null;
    }

    public static List<PullMessage> readMessages(List<MessageDurability> messageDurabilitys){
        return FileOperation.readMessages(StorageConfig.MessagePath + "mq_1",messageDurabilitys);
    }

    public static List<MessageDurability> readMessageDurability(long offset,int size,String topic){
        return FileOperation.readMessageDurability(StorageConfig.MessagePath + "mq_1",offset,size,topic);
    }


}
