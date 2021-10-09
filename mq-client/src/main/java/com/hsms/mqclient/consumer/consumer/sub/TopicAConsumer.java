package com.hsms.mqclient.consumer.consumer.sub;

import com.hsmq.data.message.PullMessage;
import com.hsms.mqclient.consumer.consumer.AbstractConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/10/1 2:47 下午
 */
public class TopicAConsumer extends AbstractConsumer {

    final static Logger log = LoggerFactory.getLogger(TopicAConsumer.class);

    @Override
    protected boolean consumeMessage(PullMessage pullMessage) {

        log.info("TopicAConsumer 消费:{}",pullMessage);
        return true;
    }
}
