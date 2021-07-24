package com.hsmq.consumer.executos;

import com.hsmq.consumer.message.ConsumerMessageQueue;
import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.message.Confirm;
import com.hsmq.data.message.PullMessage;
import com.hsmq.data.message.SendMessage;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.protocol.HsEecodeData;
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
                System.out.println("消费消息"+ pullMessage);
                try {
                    confirm(pullMessage);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }


    private void confirm(PullMessage pullMessage) throws InterruptedException {
        HsEecodeData hsEecodeData = new HsEecodeData();
        hsEecodeData.setHead(Head.toHead(MessageEnum.Req));
        HsReq<Confirm> hsReq = new HsReq<>();
        Confirm confirm = new Confirm();
        confirm.setQueueId(pullMessage.getQueueId());
        hsReq.setData(confirm);
        hsReq.setOperation(OperationEnum.Confirm.getOperation());
        hsEecodeData.setData(hsReq);
        channelFuture.channel().writeAndFlush(hsEecodeData).sync();
    }

}
