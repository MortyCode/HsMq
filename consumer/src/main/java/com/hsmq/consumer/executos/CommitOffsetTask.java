package com.hsmq.consumer.executos;

import com.hsmq.consumer.ClientStartup;
import com.hsmq.consumer.message.ConsumerMessageQueue;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 定时提交偏移量任务
 * @author ：河神
 * @date ：Created in 2021/9/30 8:42 下午
 */
public class CommitOffsetTask implements Runnable{

    final static Logger log = LoggerFactory.getLogger(ClientStartup.class);


    private final ConsumerMessageQueue consumerMessageQueue;
    private final ChannelFuture channelFuture;


    public CommitOffsetTask(ChannelFuture channelFuture,ConsumerMessageQueue consumerMessageQueue) {
        this.channelFuture = channelFuture;
        this.consumerMessageQueue = consumerMessageQueue;
    }

    @Override
    public void run() {
        Map<Integer, Long> offSetMap = consumerMessageQueue.getOffSetMap();
        log.info("offSetMap:{}",offSetMap);

    }
}
