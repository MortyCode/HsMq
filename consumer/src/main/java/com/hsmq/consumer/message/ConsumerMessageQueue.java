package com.hsmq.consumer.message;

import com.hsmq.data.message.MessageQueueData;
import com.hsmq.data.message.PullMessage;
import com.hsmq.data.message.PullMessageResp;
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

    private final Map<Integer,Long> lastMessageMap = new ConcurrentHashMap<>();

    private final String topic;

    public ConsumerMessageQueue(String topic ) {
        this.topic = topic;
    }

    public void initQueue(MessageQueueData messageQueueData){
        Integer queueSize = messageQueueData.getQueueSize();
        for (int i=0;i<queueSize;i++){
            queueMap.put(i,new ConcurrentLinkedQueue<>());
        }
        Map<Integer, Long> serverOffSetMap = messageQueueData.getOffSetMap();
        if (serverOffSetMap!=null&&serverOffSetMap.size()>0){
            offSetMap.putAll(serverOffSetMap);
            lastMessageMap.putAll(serverOffSetMap);
        }


    }

    /**
     * 将拉取的消息放入消费队列当中
     */
    public void addMessage(PullMessageResp pullMessageResp){
        if (CollectionUtils.isEmpty(pullMessageResp.getPullMessages())){
            return;
        }
        ConcurrentLinkedQueue<PullMessage> concurrentLinkedQueue = queueMap.get(pullMessageResp.getQueueId());
        if (concurrentLinkedQueue==null){
            return;
        }
        lastMessageMap.put(pullMessageResp.getQueueId(),pullMessageResp.getLastIndex());
        concurrentLinkedQueue.addAll(pullMessageResp.getPullMessages());
    }

    public Map<Integer, ConcurrentLinkedQueue<PullMessage>> getQueueMap() {
        return queueMap;
    }

    public void confirmOffset(Integer queueId,PullMessage pullMessage)  {
        offSetMap.put(queueId,pullMessage.getIndex());
    }

    public Map<Integer, Long> getOffSetMap() {
        return offSetMap;
    }

    public Map<Integer, Long> getLastMessageMap() {
        return lastMessageMap;
    }

    public String getTopic() {
        return topic;
    }
}
