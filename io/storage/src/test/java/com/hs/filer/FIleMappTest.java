package com.hs.filer;

import com.hsmq.storage.config.StorageConfig;
import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.storage.file.FileOperation;
import com.hsmq.utils.ObjectByteUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * 零拷贝
 * @author ：河神
 * @date ：Created in 2021/4/4 7:13 下午
 */
public class FIleMappTest {


    public static void main(String[] args) throws IOException, InterruptedException {


        FileChannel fileChannel =
                new RandomAccessFile(StorageConfig.MessagePath+"data-0001", "r").getChannel();


        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0,
                fileChannel.size());


        while (true){
            if (map.position()>=map.limit()){
                break;
            }
            int length = map.getInt();
            byte[] bytes = new byte[length];
            map.get(bytes);
            System.out.println(length+" : "+ObjectByteUtils.toObject(bytes).toString());
        }
        map.clear();
        fileChannel.close();


    }

    public static void  saveAndRead() throws IOException, InterruptedException{
        MessageDurability messageDurability1 = FileOperation.save(StorageConfig.MessagePath+"data-0001", "长颈鹿吃树叶");

        FileChannel fileChannel =
                new RandomAccessFile(StorageConfig.MessagePath+"data-0001", "r").getChannel();

        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_ONLY, messageDurability1.getOffset(),
                messageDurability1.getLength());

        int anInt = map.getInt();
        System.out.println(anInt);
        byte[] bytes = new byte[messageDurability1.getLength()-4];
        map.get(bytes);

        System.out.println(ObjectByteUtils.toObject(bytes).toString());

        fileChannel.close();
    }

}
