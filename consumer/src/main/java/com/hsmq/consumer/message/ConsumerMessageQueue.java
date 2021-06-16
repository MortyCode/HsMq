package com.hsmq.consumer.message;

import com.hsmq.data.message.Message;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 6:22 下午
 */
public class ConsumerMessageQueue {


    private ConcurrentLinkedQueue<Message> messageMappingQueue = new ConcurrentLinkedQueue<>();
    private static volatile ConsumerMessageQueue consumerMessageQueue;
    private  ConsumerMessageQueue(){}

    public static ConsumerMessageQueue getConsumerMessageQueue(){

        if (consumerMessageQueue!=null){
            return consumerMessageQueue;
        }else {
            synchronized (ConsumerMessageQueue.class){
                if (consumerMessageQueue!=null){
                    return consumerMessageQueue;
                }
                consumerMessageQueue = new ConsumerMessageQueue();

            }
        }
        return consumerMessageQueue;
    }

    public void addMessage(List<Message> messages){
        if (CollectionUtils.isEmpty(messages)){
            return;
        }
        messageMappingQueue.addAll(messages);
    }

    public Message getMessage(){
        return messageMappingQueue.poll();
    }

    public boolean isEmpty(){
        return messageMappingQueue.size()==0;
    }

}
