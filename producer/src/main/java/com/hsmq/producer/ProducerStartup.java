package com.hsmq.producer;

import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.message.SendMessage;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.producer.reactor.ProducerClient;
import com.hsmq.protocol.HsEecodeData;
import io.netty.channel.ChannelFuture;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:25 下午
 */
public class ProducerStartup {


    public static void main(String[] args) throws InterruptedException {
        ProducerClient baseConsumer = new ProducerClient("127.0.0.1", 9001);
        baseConsumer.start();

        ChannelFuture channelFuture = baseConsumer.getChannelFuture();


        for (int i=1;;i++){
            HsEecodeData hsEecodeData = new HsEecodeData();
            hsEecodeData.setHead(Head.toHead(MessageEnum.Req));

            HsReq<SendMessage> hsReq = new HsReq<>();
            SendMessage sendMessage = new SendMessage();

            hsReq.setOperation(OperationEnum.SendMessage.getOperation());
            if (i%2==0){
                sendMessage.setTopic("TopicA");
                sendMessage.setTag("tagA");
            }else{
                sendMessage.setTopic("TopicB");
                sendMessage.setTag("tagB");
            }
            sendMessage.setBody("消息---"+i);
            hsReq.setData(sendMessage);
            hsEecodeData.setData(hsReq);

            Thread.sleep(100L);
            channelFuture.channel().writeAndFlush(hsEecodeData).sync();
        }
    }

}
