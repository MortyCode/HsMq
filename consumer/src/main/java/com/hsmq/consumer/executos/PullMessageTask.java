package com.hsmq.consumer.executos;

import com.hsmq.consumer.message.ConsumerMessageQueue;
import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.message.Pull;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.protocol.HsEecodeData;
import io.netty.channel.ChannelFuture;

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
            if (consumerMessageQueue.isEmpty()){
                try {
                    pull();
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pull() throws InterruptedException {

        HsEecodeData hsEecodeData = new HsEecodeData();
        hsEecodeData.setHead(Head.toHead(MessageEnum.Req));
        HsReq<Pull> hsReq = new HsReq<>();
        Pull pull = new Pull();
        pull.setTopic(topic);
        pull.setConsumerName("AConsumer");
        pull.setSize(10);
        hsReq.setData(pull);
        hsReq.setOperation(OperationEnum.Pull.getOperation());
        hsEecodeData.setData(hsReq);

        channelFuture.channel().writeAndFlush(hsEecodeData).sync();
    }
}
