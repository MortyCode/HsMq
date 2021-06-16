package com.hsmq.consumer.executos;

import com.hsmq.consumer.message.ConsumerMessageQueue;
import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.message.Confirm;
import com.hsmq.data.message.Message;
import com.hsmq.data.message.Pull;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.protocol.HsEecodeData;
import io.netty.channel.ChannelFuture;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 3:17 下午
 */
public class ExecutorMessageTask implements Runnable{


    private static ConsumerMessageQueue consumerMessageQueue;
    private ChannelFuture channelFuture;
    private String topic;
    static {
         consumerMessageQueue = ConsumerMessageQueue.getConsumerMessageQueue();
    }

    public ExecutorMessageTask(ChannelFuture channelFuture, String topic) {
        this.channelFuture = channelFuture;
        this.topic = topic;
    }

    @Override
    public void run() {
        while (true){
            Message message = consumerMessageQueue.getMessage();
            if (message!=null){
                System.out.println("消费消息"+message);
                try {
                    confirm();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }
    }

    private void confirm() throws InterruptedException {
        HsEecodeData hsEecodeData = new HsEecodeData();
        hsEecodeData.setHead(Head.toHead(MessageEnum.Req));
        HsReq<Confirm> hsReq = new HsReq<>();
        Confirm confirm = new Confirm();

        hsReq.setData(confirm);
        hsReq.setOperation(OperationEnum.Confirm.getOperation());
        hsEecodeData.setData(hsReq);
        channelFuture.channel().writeAndFlush(hsEecodeData).sync();
    }

}
