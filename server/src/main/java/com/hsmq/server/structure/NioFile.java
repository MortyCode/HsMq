package com.hsmq.server.structure;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author ：河神
 * @date ：Created in 2021/6/4 5:00 下午
 */
public class NioFile {

    public static void main(String[] args) throws Exception {

        FileChannel fileChannel =
                new RandomAccessFile("base", "rw").getChannel();
        fileChannel.force(true);
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);
        byteBuffer.put("2ab3abc4abcd".getBytes());
        byteBuffer.flip();

        fileChannel.write(byteBuffer);
        fileChannel.force(true);

        FileChannel fileChannel2 =
                new RandomAccessFile("base2", "rw").getChannel();

        fileChannel.transferTo(0,2,fileChannel2);

        fileChannel2.force(true);

    }

}
