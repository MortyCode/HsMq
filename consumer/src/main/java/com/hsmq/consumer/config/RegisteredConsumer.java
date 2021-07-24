package com.hsmq.consumer.config;

import com.hsmq.consumer.executos.ExecutorMessageTask;
import com.hsmq.consumer.executos.PullMessageTask;
import com.hsmq.consumer.message.ConsumerMessageQueue;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ：河神
 * @date ：Created in 2021/7/11 2:55 下午
 */
public class RegisteredConsumer {

    private final ChannelFuture channelFuture;

    public RegisteredConsumer(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public  void registeredConsumer(String topic){
        ThreadPoolExecutor executor = ExecutorService.getExecutor();
        ConsumerMessageQueue consumerMessageQueue = new ConsumerMessageQueue(topic);
        executor.execute(new PullMessageTask(channelFuture , consumerMessageQueue));
        executor.execute(new ExecutorMessageTask(channelFuture ,consumerMessageQueue));
    }
}
