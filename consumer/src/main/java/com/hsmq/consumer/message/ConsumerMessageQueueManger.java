package com.hsmq.consumer.message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 6:22 下午
 */
public class ConsumerMessageQueueManger {

    public static Map<String,ConsumerMessageQueue> consumerMessageQueueMap = new ConcurrentHashMap<>(16);

    public static void registeredQueue(String topic,ConsumerMessageQueue consumerMessageQueue ){
        consumerMessageQueueMap.put(topic,consumerMessageQueue);
    }

    public static ConsumerMessageQueue getQueue(String topic){
        return consumerMessageQueueMap.get(topic);
    }


}
