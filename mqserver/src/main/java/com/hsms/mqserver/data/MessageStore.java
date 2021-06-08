package com.hsms.mqserver.data;

import com.hsmq.data.Message;
import com.hsmq.enums.ResultEnum;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:10 下午
 */
public class MessageStore {

    private static ConcurrentHashMap<String,ConcurrentLinkedQueue<Message>> data = new ConcurrentHashMap<>();

    public Message pull(Message message){

        Message data = pullMessage(message.getTopic());

        if (data==null){
            return message;
        }
        message.setBody(data.getBody());
        message.setTag(data.getTag());
        message.setTopic(data.getTopic());
        message.setMsgId(data.getMsgId());
        return message;
    }

    public Message pullMessage(String topic){
        ConcurrentLinkedQueue<Message> queue;
        if ((queue=data.get(topic))==null){
            return null;
        }
        return queue.poll();
    }

    public void save(Message message){
        ConcurrentLinkedQueue<Message> queue = data.get(message.getTopic());
        if (queue==null){
            synchronized (this){
                queue = data.get(message.getTopic());
                if (queue==null){
                    queue = new ConcurrentLinkedQueue<>();
                    data.put(message.getTopic(),queue);
                }
            }
        }
        queue.add(message);
    }

}
