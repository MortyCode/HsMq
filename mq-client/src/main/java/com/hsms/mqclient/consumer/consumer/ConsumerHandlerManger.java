package com.hsms.mqclient.consumer.consumer;

import com.hsmq.data.message.PullMessage;
import com.hsms.mqclient.consumer.config.RegisteredConsumer;
import com.hsms.mqclient.consumer.consumer.sub.TopicAConsumer;
import com.hsms.mqclient.consumer.consumer.sub.TopicBConsumer;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：河神
 * @date ：Created in 2021/10/1 2:45 下午
 */
public class ConsumerHandlerManger {

    private static final Map<String, Map<String,AbstractConsumer>>  allConsumerMap = new HashMap<>();

    final static Logger log = LoggerFactory.getLogger(TopicBConsumer.class);


    public static void initConsumer(String consumerGroup, String[] topics, ChannelFuture channelFuture){
        log.info("initConsumer start");

        //注册消费组对应的消费者
        Map<String,AbstractConsumer> consumerMap = new HashMap<>();
        consumerMap.put("TopicA",new TopicAConsumer());
        consumerMap.put("TopicB",new TopicBConsumer());
        allConsumerMap.put(consumerGroup,consumerMap);

        //初始化消费者
        RegisteredConsumer.setChannelFuture(channelFuture);
        RegisteredConsumer.init(consumerGroup,topics);

        log.info("initConsumer end");
    }

    public static boolean consumer(String consumerGroup,PullMessage pullMessage){

        Map<String, AbstractConsumer> consumerMap = allConsumerMap.get(consumerGroup);
        if (consumerMap==null){
            log.error("ConsumerGroup:{} Not Exists Consumer",pullMessage.getTopic());
            return false;
        }
        AbstractConsumer abstractConsumer = consumerMap.get(pullMessage.getTopic());
        if (abstractConsumer==null){
            log.error("Topic:{} Not Exists Consumer",pullMessage.getTopic());
            return false;
        }
        return abstractConsumer.consumer(pullMessage);
    }

}
