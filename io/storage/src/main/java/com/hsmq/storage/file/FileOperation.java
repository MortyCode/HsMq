package com.hsmq.storage.file;

import com.hsmq.common.exception.FileException;
import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.utils.ObjectByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 1:46 下午
 */
public class FileOperation {

    final static Logger log = LoggerFactory.getLogger(FileOperation.class);

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


    public static byte[] read(FileChannel fileChannel,long offset) throws IOException, InterruptedException{
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        fileChannel.read(byteBuffer,offset);
        byteBuffer.flip();

        int length = byteBuffer.getInt();

        ByteBuffer dataBuffer = ByteBuffer.allocate(length);
        fileChannel.read(dataBuffer,offset+4);
        dataBuffer.flip();

        byte[] bytes = new byte[length];
        dataBuffer.get(bytes);
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


    public static byte[] read(FileChannel fileChannel,long offset,int length) throws IOException, InterruptedException{
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        fileChannel.read(byteBuffer,offset+4);
        byteBuffer.flip();

        byte[] bytes = new byte[length-4];

        byteBuffer.get(bytes);
        return bytes;
    }

    public static List<SendMessage> readMessages(String fileName, List<MessageDurability> messageDurability){
        try {

            FileChannel fileChannel =
                    new RandomAccessFile(fileName, "r").getChannel();

            List<SendMessage> data = new ArrayList<>();
            for (MessageDurability durability : messageDurability) {
                byte[] read = read(fileChannel, durability.getOffset(), durability.getLength());
                Object object = ObjectByteUtils.toObject(read);
                if (object instanceof SendMessage){
                    data.add((SendMessage)object);
                }
            }
            return data;

        } catch (IOException | InterruptedException e) {
            log.error("read filer error",e);
        }
        return null;
    }

}
