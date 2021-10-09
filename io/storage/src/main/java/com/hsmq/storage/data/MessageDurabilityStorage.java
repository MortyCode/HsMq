package com.hsmq.storage.data;

import com.hsmq.storage.config.StorageConfig;
import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.storage.file.FileOperation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/10/3 11:02 上午
 */
public class MessageDurabilityStorage {


    public static void saveMessageQueue(Integer queueId,String topic, List<MessageDurability> data) throws IOException {
        String queueFileName = StorageConfig.MessagePath + topic +StorageConfig.Queue + queueId;
        new File(StorageConfig.MessagePath + topic +StorageConfig.Queue ).mkdirs();
        try {
            FileOperation.save(queueFileName, data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<MessageDurability>  readMessageQueue(Integer queueId,String topic,long index){
        String queueFileName = StorageConfig.MessagePath + topic +StorageConfig.Queue + queueId;
        new File(StorageConfig.MessagePath + topic +StorageConfig.Queue ).mkdirs();
        try {
            return FileOperation.readMessageQueue(queueFileName, index);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
