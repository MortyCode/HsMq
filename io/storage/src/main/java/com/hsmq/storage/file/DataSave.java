package com.hsmq.storage.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ：河神
 * @date ：Created in 2021/4/4 7:13 下午
 */
public class DataSave {

    public static void main(String[] args)throws IOException, InterruptedException {
        save();
        read();
    }



    public static void save() throws IOException, InterruptedException{

        RandomAccessFile rws = new RandomAccessFile("data-0001", "rws");


        FileChannel fileChannel = rws.getChannel();

        fileChannel.force(true);
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);
        byteBuffer.putInt("测试\n".getBytes().length);
        byteBuffer.put("测试\n".getBytes());

        byteBuffer.putInt("中国人\n".getBytes().length);
        byteBuffer.put("中国人\n".getBytes());

        byteBuffer.putInt("轩辕剑天之痕\n".getBytes().length);
        byteBuffer.put("轩辕剑天之痕\n".getBytes());
        byteBuffer.flip();

        fileChannel.position(fileChannel.size());
        fileChannel.write(byteBuffer);
        fileChannel.force(true);
    }

    public static void read() throws IOException, InterruptedException{
        FileChannel fileChannel =
                new RandomAccessFile("data-0001", "rw").getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int)fileChannel.size());
        fileChannel.read(byteBuffer);

        byteBuffer.flip();

        while (byteBuffer.position()<byteBuffer.limit()){
            int a = byteBuffer.getInt();
            byte[] data = new byte[a];
            byteBuffer.get(data);
            System.out.println("length:"+a+" data:"+new String(data));
        }

    }

}
