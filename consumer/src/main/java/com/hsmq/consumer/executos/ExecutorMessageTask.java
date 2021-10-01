package com.hsmq.consumer.executos;

import com.hsmq.consumer.consumer.ConsumerHandlerManger;
import com.hsmq.consumer.message.ConsumerMessageQueue;
import com.hsmq.data.message.PullMessage;
import io.netty.channel.ChannelFuture;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 3:17 下午
 */
public class ExecutorMessageTask implements Runnable{


    private final ConsumerMessageQueue consumerMessageQueue;
    private final ChannelFuture channelFuture;
    private final String topic;

    public ExecutorMessageTask(ChannelFuture channelFuture,
                               ConsumerMessageQueue consumerMessageQueue) {
        this.channelFuture = channelFuture;
        this.topic = consumerMessageQueue.getTopic();
        this.consumerMessageQueue = consumerMessageQueue;
    }

    @Override
    public void run() {
        while (true){
            PullMessage pullMessage = consumerMessageQueue.getMessage();
            if (pullMessage !=null){
                boolean consumer = ConsumerHandlerManger.consumer(pullMessage);
                if (consumer){
                    System.out.println("消费消息"+ pullMessage);
                    consumerMessageQueue.confirmOffset(pullMessage);
                }
            }
        }
    }



}
