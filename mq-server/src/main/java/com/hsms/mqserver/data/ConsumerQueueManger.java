package com.hsms.mqserver.data;

import com.hsmq.data.message.SendMessage;
import com.hsmq.storage.config.TopicConfig;
import com.hsmq.storage.durability.MessageDurability;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 5:20 下午
 */
public class ConsumerQueueManger {

    private static final ConcurrentMap<String, TopicListener> DATA = new ConcurrentHashMap<>();

    public static void registerTopic(TopicConfig topicConfig){
        DATA.put(topicConfig.getTopicName(), new TopicListener(topicConfig));
    }

    public static TopicListener getTopicListener(String topic){
        return DATA.get(topic);
    }

    public static boolean existsTopic(SendMessage sendMessage){
        return getTopicListener(sendMessage.getTopic())!=null;
    }

    public static boolean pushConsumerQueue(SendMessage sendMessage, MessageDurability messageDurability){
        TopicListener topicListener = getTopicListener(sendMessage.getTopic());
        return topicListener.addMsg2Queue(sendMessage,messageDurability);
    }

}
