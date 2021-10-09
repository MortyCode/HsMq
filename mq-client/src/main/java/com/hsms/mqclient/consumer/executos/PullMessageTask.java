package com.hsms.mqclient.consumer.executos;

import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.message.Pull;
import com.hsmq.data.message.PullMessage;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.protocol.HsEecodeData;
import com.hsms.mqclient.consumer.config.RegisteredConsumer;
import com.hsms.mqclient.consumer.consumer.ConsumerHandlerManger;
import com.hsms.mqclient.consumer.message.ConsumerMessageQueue;
import io.netty.channel.ChannelFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 3:17 下午
 */
public class PullMessageTask implements Runnable{

    private final ConsumerMessageQueue consumerMessageQueue;
    private final ChannelFuture channelFuture;
    private final String topic;

    public PullMessageTask(ChannelFuture channelFuture,ConsumerMessageQueue consumerMessageQueue) {
        this.channelFuture = channelFuture;
        this.topic = consumerMessageQueue.getTopic();
        this.consumerMessageQueue = consumerMessageQueue;
    }

    @Override
    public void run() {
        while (true){
            //尚未初始化完成
            if (!RegisteredConsumer.isInit()){
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            Map<Integer, ConcurrentLinkedQueue<PullMessage>> queueMap = consumerMessageQueue.getQueueMap();
            for (Integer queueId : queueMap.keySet()) {
                try {
                    pull(queueId);
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pull(Integer queueId) throws InterruptedException {
        HsEecodeData hsEecodeData = new HsEecodeData();
        hsEecodeData.setHead(Head.toHead(MessageEnum.Req));

        Map<Integer, Long> lastMessageMap = consumerMessageQueue.getLastMessageMap();

        HsReq<Pull> hsReq = new HsReq<>();

        Pull pull = new Pull();
        pull.setTopic(topic);
        pull.setQueueId(queueId);
        pull.setConsumerName(ConsumerHandlerManger.getConsumerName());
        pull.setSize(10);

        if (lastMessageMap.get(queueId)==null){
            pull.setOffset(0);
        }else{
            pull.setOffset(lastMessageMap.get(queueId)+1);
        }

        hsReq.setData(pull);
        hsReq.setOperation(OperationEnum.Pull.getOperation());
        hsEecodeData.setData(hsReq);

        channelFuture.channel().writeAndFlush(hsEecodeData).sync();
    }
}
