package com.hs.filer;

import com.hsmq.storage.config.StorageConfig;
import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.storage.file.FileOperation;
import com.hsmq.utils.ObjectByteUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 零拷贝
 * @author ：河神
 * @date ：Created in 2021/4/4 7:13 下午
 */
public class FIleTest {

    private static InternalLogger logger = InternalLoggerFactory.getInstance(FIleTest.class);


    public static void main(String[] args) throws IOException, InterruptedException {

        MessageDurability messageDurability1 = FileOperation.save(StorageConfig.MessagePath+"data-0001", "长颈鹿吃树叶");
        MessageDurability messageDurability2 = FileOperation.save(StorageConfig.MessagePath+"data-0001", "长颈鹿吃树叶");
        MessageDurability messageDurability3 = FileOperation.save(StorageConfig.MessagePath+"data-0001", "长颈鹿吃树叶");

        FileChannel fileChannel =
                new RandomAccessFile(StorageConfig.MessagePath+"data-0001", "r").getChannel();

        List<MessageDurability> data  = new ArrayList<>();

        data.add(messageDurability1);
        data.add(messageDurability2);
        data.add(messageDurability3);

        for (MessageDurability datum : data) {
            byte[] read1 = FileOperation.read(fileChannel, datum.getOffset(),datum.getLength());
            logger.info(ObjectByteUtils.toObject(read1).toString());
        }
        fileChannel.close();


    }

}
