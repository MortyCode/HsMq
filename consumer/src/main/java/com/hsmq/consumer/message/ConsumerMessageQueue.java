package com.hsmq.consumer.message;

import com.hsmq.data.message.PullMessage;
import com.hsmq.data.message.SendMessage;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 6:22 下午
 */
public class ConsumerMessageQueue {


    private final Map<Integer,ConcurrentLinkedQueue<PullMessage>> queueMap = new ConcurrentHashMap<>();

    private final Map<Integer,Long> offSetMap = new ConcurrentHashMap<>();

    private final String topic;

    public ConsumerMessageQueue(String topic ) {
        this.topic = topic;
    }

    public void addMessage(List<PullMessage> pullMessages){
        if (CollectionUtils.isEmpty(pullMessages)){
            return;
        }
        for (PullMessage pullMessage : pullMessages) {
            Integer queueId = pullMessage.getQueueId();
            ConcurrentLinkedQueue<PullMessage> queue ;
            if ((queue=queueMap.get(queueId))==null){
                queue = new ConcurrentLinkedQueue<>();
                queueMap.put(queueId,queue);
            }
            queue.add(pullMessage);
        }
    }

    public PullMessage getMessage(){
        //获取队列中的消息

        return null;
    }

    public boolean isEmpty(){
        return false;
    }

    public String getTopic() {
        return topic;
    }
}
