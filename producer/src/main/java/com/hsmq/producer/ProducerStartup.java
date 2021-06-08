package com.hsmq.producer;

import com.hsmq.data.Message;
import com.hsmq.enums.MessageEnum;
import com.hsmq.producer.reactor.ProducerClient;
import io.netty.channel.ChannelFuture;

import java.util.UUID;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:25 下午
 */
public class ProducerStartup {


    public static void main(String[] args) throws InterruptedException {
        ProducerClient baseConsumer = new ProducerClient("127.0.0.1", 9001);
        baseConsumer.start();

        ChannelFuture channelFuture = baseConsumer.getChannelFuture();

        Message message = new Message();
        message.setType(MessageEnum.Message.getCode());
        for (int i=1;;i++){

            if (i%2==0){
                message.setTopic("TopicA");
                message.setTag("tagA");
            }else{
                message.setTopic("TopicB");
                message.setTag("tagB");
            }

            Thread.sleep(1000L);
            message.setBody("消息---"+i);
            message.setMsgId(UUID.randomUUID().toString());
            channelFuture.channel().writeAndFlush(message);
        }
    }

}
