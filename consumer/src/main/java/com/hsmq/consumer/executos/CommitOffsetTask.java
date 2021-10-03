package com.hsmq.consumer.executos;

import com.hsmq.consumer.message.ConsumerMessageQueue;
import io.netty.channel.ChannelFuture;

/**
 * 定时提交偏移量任务
 * @author ：河神
 * @date ：Created in 2021/9/30 8:42 下午
 */
public class CommitOffsetTask implements Runnable{

    private final ConsumerMessageQueue consumerMessageQueue;
    private final ChannelFuture channelFuture;


    public CommitOffsetTask(ChannelFuture channelFuture,ConsumerMessageQueue consumerMessageQueue) {
        this.channelFuture = channelFuture;
        this.consumerMessageQueue = consumerMessageQueue;
    }

    @Override
    public void run() {




    }
}
