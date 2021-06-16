package com.hsmq.storage.data;

import com.hsmq.data.message.Message;
import com.hsmq.storage.config.StorageConfig;
import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.storage.file.FileOperation;
import com.hsmq.utils.ObjectByteUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/6/11 3:12 下午
 */
public class MessageStorage {

    private static InternalLogger logger = InternalLoggerFactory.getInstance(MessageStorage.class);

    public MessageDurability saveMessage(Message message){
        try {
            synchronized (this){
                return FileOperation.save(StorageConfig.MessagePath+"mq_1",message);
            }
        } catch (IOException | InterruptedException e) {
            logger.error("save filer error",e);
        }
        return null;
    }

    public Message readMessage(MessageDurability messageDurability){
        try {
            byte[] read = FileOperation.read(StorageConfig.MessagePath + "mq_1", messageDurability.getOffset());
            Object object = ObjectByteUtils.toObject(read);

            if (object instanceof Message){
                return (Message)object;
            }
            return null;
        } catch (IOException | InterruptedException e) {
            logger.error("read filer error",e);
        }
        return null;
    }

    public List<Message> readMessages(List<MessageDurability> messageDurabilitys){
        return FileOperation.readMessages(StorageConfig.MessagePath + "mq_1",messageDurabilitys);
    }


}
