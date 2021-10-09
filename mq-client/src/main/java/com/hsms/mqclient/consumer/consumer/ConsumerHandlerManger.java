package com.hsms.mqclient.consumer.consumer;

import com.hsmq.data.message.PullMessage;
import com.hsms.mqclient.consumer.consumer.sub.TopicAConsumer;
import com.hsms.mqclient.consumer.consumer.sub.TopicBConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：河神
 * @date ：Created in 2021/10/1 2:45 下午
 */
public class ConsumerHandlerManger {

    private static final ConcurrentHashMap<String,AbstractConsumer> consumerMap = new ConcurrentHashMap<>();
    private static String consumerName ;

    public static String getConsumerName() {
        return consumerName;
    }

    final static Logger log = LoggerFactory.getLogger(TopicBConsumer.class);


    public static void initConsumer(String consumerName){
        log.info("initConsumer start");
        ConsumerHandlerManger.consumerName = consumerName;
        consumerMap.put("TopicA",new TopicAConsumer());
        consumerMap.put("TopicB",new TopicBConsumer());
        log.info("initConsumer end");
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
