package com.hsmq.producer;

import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.Message;
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

            HsReq<Message> hsReq = new HsReq<>();
            Message message = new Message();

            hsReq.setOperation(OperationEnum.Message.getOperation());
            if (i%2==0){
                message.setTopic("TopicA");
                message.setTag("tagA");
            }else{
                message.setTopic("TopicB");
                message.setTag("tagB");
            }
            message.setBody("消息---"+i);
            hsReq.setData(message);
            hsEecodeData.setData(hsReq);

            Thread.sleep(1000L);
            channelFuture.channel().writeAndFlush(hsEecodeData).sync();
        }
    }

}
