package com.hsmq.storage.file;

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

        FileChannel fileChannel =
            new RandomAccessFile("data-0001", "rw").getChannel();
        fileChannel.force(true);

        ByteBuffer byteBuffer = ByteBuffer.allocate(50);
        byteBuffer.put("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA".getBytes());
        byteBuffer.flip();

        fileChannel.write(byteBuffer);

        fileChannel.force(true);
//        fileChannel.transferTo()
        Thread.sleep(1000000L);



    }

}
