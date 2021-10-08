package com.hsmq.storage.file;

import com.hsmq.common.exception.FileException;
import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.durability.MessageDurability;
import com.hsmq.utils.ObjectByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
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

    private static final int MessageDurabilityLength = 20;

    public static void save(String fileName,List<MessageDurability> data) throws IOException {
        if (data==null||data.size()==0){
            return;
        }

        RandomAccessFile rws = new RandomAccessFile(fileName, "rw");
        FileChannel fileChannel = rws.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(data.size()* MessageDurabilityLength);

        for (MessageDurability messageDurability : data) {
            byte[] bytes = ObjectByteUtils.toByteArray(messageDurability);
            if (bytes==null){
                throw new FileException("文件转化异常：object not can cast bytes");
            }
            byteBuffer.putLong(messageDurability.getOffset());
            byteBuffer.putInt(messageDurability.getLength());
            byteBuffer.putInt(messageDurability.getTagHashcode());
            byteBuffer.putLong(fileChannel.size()/MessageDurabilityLength);
        }

        byteBuffer.flip();
        fileChannel.position(fileChannel.size());
        fileChannel.write(byteBuffer);

        fileChannel.force(true);
        fileChannel.close();
    }


    public static List<MessageDurability> readMessageQueue(String fileName,long index) throws IOException {
        List<MessageDurability> data = new ArrayList<>();

        RandomAccessFile rws = new RandomAccessFile(fileName, "rw");
        long offset = index * MessageDurabilityLength;

        FileChannel fileChannel = rws.getChannel();
        while (true){
            ByteBuffer byteBuffer = ByteBuffer.allocate(MessageDurabilityLength);
            int read = fileChannel.read(byteBuffer, offset);
            if (read<=0){
                break;
            }
            byteBuffer.flip();
            MessageDurability messageDurability = new MessageDurability();
            messageDurability.setOffset(byteBuffer.getLong());
            messageDurability.setLength(byteBuffer.getInt());
            messageDurability.setTagHashcode(byteBuffer.getInt());

            offset+=MessageDurabilityLength;

            data.add(messageDurability);
        }
        return data;
    }



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

    public static byte[] read(FileChannel fileChannel,long offset,int length) throws IOException{
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        fileChannel.read(byteBuffer,offset+4);
        byteBuffer.flip();
        byte[] bytes = new byte[length-4];
        byteBuffer.get(bytes);
        return bytes;
    }

    public static List<SendMessage> readMessages(String fileName, List<MessageDurability> messageDurability){
        List<SendMessage> data = new ArrayList<>();
        try {
            FileChannel fileChannel =
                    new RandomAccessFile(fileName, "r").getChannel();
            for (MessageDurability durability : messageDurability) {
                byte[] read = read(fileChannel, durability.getOffset(), durability.getLength());
                Object object = ObjectByteUtils.toObject(read);
                if (object==null){
                    continue;
                }
                if (object instanceof SendMessage){
                    SendMessage sendMessage = (SendMessage) object;
                    sendMessage.setOffset(durability.getOffset());
                    data.add(sendMessage);
                }
            }
            return data;
        } catch (Exception e) {
            log.error("read filer error",e);
        }
        return data;
    }



    public static List<MessageDurability> readMessageDurability(String fileName, long offset,int size,String topic){
        List<MessageDurability> data = new ArrayList<>();
        try {
            FileChannel fileChannel =
                    new RandomAccessFile(fileName, "r").getChannel();
            for (int i=0;i<size;i++){
                ByteBuffer byteBuffer = readBuff(fileChannel, offset, 4);
                if (byteBuffer==null){
                    break;
                }

                int length = byteBuffer.getInt();
                MessageDurability messageDurability = new MessageDurability();
                messageDurability.setOffset(offset);
                messageDurability.setLength(length+4);

                offset = offset+4;
                ByteBuffer bodyBuffer = readBuff(fileChannel, offset, length);
                offset = offset+length;

                if (bodyBuffer==null){
                    continue;
                }
                byte[] bytes = new byte[length];
                bodyBuffer.get(bytes);
                SendMessage sendMessage = (SendMessage)ObjectByteUtils.toObject(bytes);
                if (sendMessage==null||!topic.equals(sendMessage.getTopic())){
                    continue;
                }
                data.add(messageDurability);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static ByteBuffer readBuff(FileChannel fileChannel, long offset, int length) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(length);
        int read = fileChannel.read(byteBuffer, offset);
        if (read<=0){
            return null;
        }
        byteBuffer.flip();
        return byteBuffer;
    }


}
