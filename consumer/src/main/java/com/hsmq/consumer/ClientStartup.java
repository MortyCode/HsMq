package com.hsmq.consumer;

import com.hsmq.consumer.executos.ExecutorMessageTask;
import com.hsmq.consumer.executos.PullMessageTask;
import com.hsmq.consumer.reactor.ConsumerClient;
import io.netty.channel.ChannelFuture;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:25 下午
 */
public class ClientStartup {


    public static void main(String[] args) throws InterruptedException {
        ConsumerClient baseConsumer = new ConsumerClient("127.0.0.1", 9001);
        baseConsumer.start();

        String topic = args[0];


        ChannelFuture channelFuture = baseConsumer.getChannelFuture();

        new Thread(new PullMessageTask(channelFuture, topic)).start();
        new Thread(new ExecutorMessageTask(channelFuture,topic)).start();


    }

}
