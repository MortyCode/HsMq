package com.hsmq.consumer.consumer;

import com.hsmq.consumer.consumer.sub.TopicAConsumer;
import com.hsmq.consumer.consumer.sub.TopicBConsumer;
import com.hsmq.data.message.PullMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：河神
 * @date ：Created in 2021/10/1 2:45 下午
 */
public class ConsumerHandlerManger {

    private static final ConcurrentHashMap<String,AbstractConsumer> consumerMap = new ConcurrentHashMap<>();
    final static Logger log = LoggerFactory.getLogger(TopicBConsumer.class);

    public static void initConsumer(){
        consumerMap.put("TopicA",new TopicAConsumer());
        consumerMap.put("TopicB",new TopicBConsumer());
    }

    public static boolean consumer(PullMessage pullMessage){
        AbstractConsumer abstractConsumer = consumerMap.get(pullMessage.getTopic());
        if (abstractConsumer==null){
            log.error("Topic:{} Not Exists Consumer",pullMessage.getTopic());
            return false;
        }
        return abstractConsumer.consumer(pullMessage);
    }

}
