package com.hsmq.storage.file;

import com.hsmq.common.exception.FileException;
import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.utils.ObjectByteUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 1:46 下午
 */
public class FileOperation {

    public static MessageDurability save(String fileName,Object object) throws IOException, InterruptedException{

        RandomAccessFile rws = new RandomAccessFile(fileName, "rw");
        FileChannel fileChannel = rws.getChannel();

        byte[] bytes = ObjectByteUtils.toByteArray(object);
        if (bytes==null){
            throw new FileException("文件转化异常：object not can cast bytes");
        }

        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length+4);
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();

        MessageDurability messageDurability = new MessageDurability();
        messageDurability.setLength(byteBuffer.limit());
        messageDurability.setOffset(fileChannel.size());

        fileChannel.position(fileChannel.size());
        fileChannel.write(byteBuffer);
        fileChannel.force(true);
        fileChannel.close();

        return messageDurability;
    }

    public static byte[] read(String fileName,long offset,int length) throws IOException, InterruptedException{
        FileChannel fileChannel =
                new RandomAccessFile(fileName, "r").getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        fileChannel.read(byteBuffer,offset+4);
        byteBuffer.flip();

        byte[] bytes = new byte[length-4];
        fileChannel.close();
        return bytes;
    }

    public static byte[] read(String fileName,long offset) throws IOException, InterruptedException{
        FileChannel fileChannel =
                new RandomAccessFile(fileName, "r").getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        fileChannel.read(byteBuffer,offset);
        byteBuffer.flip();

        int length = byteBuffer.getInt();

        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        fileChannel.read(dataBuffer,offset+4);
        dataBuffer.flip();

        byte[] bytes = new byte[length];
        dataBuffer.get(bytes);
        fileChannel.close();
        return bytes;
    }

}
