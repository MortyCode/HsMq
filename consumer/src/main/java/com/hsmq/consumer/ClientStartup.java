package com.hsmq.consumer;

import com.hsmq.consumer.reactor.ConsumerClient;
import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.Message;
import com.hsmq.data.Pull;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.protocol.HsBaseData;
import com.hsmq.protocol.HsEecodeData;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        HsEecodeData hsEecodeData = new HsEecodeData();
        hsEecodeData.setHead(Head.toHead(MessageEnum.Req));
        HsReq<Pull> hsReq = new HsReq<>();
        Pull pull = new Pull();
        pull.setTopic(topic);
        pull.setConsumerName("AConsumer");
        hsReq.setData(pull);
        hsReq.setOperation(OperationEnum.Pull.getOperation());
        hsEecodeData.setData(hsReq);

        for (;;){
            try {
                Thread.sleep(1L);
                channelFuture.channel().writeAndFlush(hsEecodeData).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
