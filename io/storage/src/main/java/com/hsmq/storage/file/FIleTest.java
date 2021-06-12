package com.hsmq.storage.file;

import com.hsmq.storage.config.StorageConfig;
import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.utils.ObjectByteUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 零拷贝
 * @author ：河神
 * @date ：Created in 2021/4/4 7:13 下午
 */
public class FIleTest {

    private static InternalLogger logger = InternalLoggerFactory.getInstance(FIleTest.class);


    public static void main(String[] args) throws IOException, InterruptedException {

        MessageDurability messageDurability = FileOperation.save(StorageConfig.MessagePath+"data-0001", "长颈鹿吃树叶");
        byte[] read = FileOperation.read(StorageConfig.MessagePath+"data-0001", messageDurability.getOffset());
        logger.info(ObjectByteUtils.toObject(read).toString());
    }

}
