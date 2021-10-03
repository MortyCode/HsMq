package com.hsmq.consumer.consumer.sub;

import com.hsmq.consumer.consumer.AbstractConsumer;
import com.hsmq.data.message.PullMessage;
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

        log.info("TopicAConsumer:{}",pullMessage);
        return true;
    }
}
