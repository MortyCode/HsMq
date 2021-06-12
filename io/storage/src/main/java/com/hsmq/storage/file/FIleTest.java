package com.hsmq.storage.file;

import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.utils.ObjectByteUtils;

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

    public static void main(String[] args) throws IOException, InterruptedException {

        MessageDurability messageDurability = FileOperation.save("data-0001", "长颈鹿");
        byte[] read = FileOperation.read("data-0001", messageDurability.getOffset());
        System.out.println(ObjectByteUtils.toObject(read).toString());

    }

}
