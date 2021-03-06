package com.hsms.mqclient.consumer.executos;

import com.hsmq.data.message.PullMessage;
import com.hsms.mqclient.consumer.config.ExecutorService;
import com.hsms.mqclient.consumer.consumer.ConsumerHandlerManger;
import com.hsms.mqclient.consumer.message.ConsumerMessageQueue;
import io.netty.channel.ChannelFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 3:17 下午
 */
public class ExecutorMessageTask implements Runnable{


    private final ConsumerMessageQueue consumerMessageQueue;
    private final ChannelFuture channelFuture;
    private final String topic;
    private final String consumerGroup;

    public ExecutorMessageTask(ChannelFuture channelFuture,
                               ConsumerMessageQueue consumerMessageQueue) {
        this.channelFuture = channelFuture;
        this.topic = consumerMessageQueue.getTopic();
        this.consumerGroup = consumerMessageQueue.getConsumerGroup();
        this.consumerMessageQueue = consumerMessageQueue;
    }

    @Override
    public void run() {
        ThreadPoolExecutor executor = ExecutorService.getExecutor();
        do {
            Map<Integer, ConcurrentLinkedQueue<PullMessage>> queueMap = consumerMessageQueue.getQueueMap();
            queueMap.forEach((queueId,queue)->{
                PullMessage pullMessage = queue.poll();
                if (pullMessage != null) {
                    boolean consumer = ConsumerHandlerManger.consumer(consumerGroup,pullMessage);
                    if (consumer) {
                        consumerMessageQueue.confirmOffset(queueId,pullMessage);
                    }
                }
            });
        } while (!Thread.interrupted());
    }



}
