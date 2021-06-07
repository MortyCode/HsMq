package com.hsmq.client;

import com.hsmq.client.client.ObjectClient;
import com.hsmq.data.Message;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.util.UUID;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:25 下午
 */
public class ClientObjectStartup {


    public static void main(String[] args) throws InterruptedException {
        ObjectClient baseConsumer = new ObjectClient("127.0.0.1", 9001);
        baseConsumer.start();
        ChannelFuture channelFuture = null;
        try {
            channelFuture = baseConsumer.getChannelFuture();

            Message message = new Message();
            message.setTopic("TopicA");
            message.setTag("TagA");
            message.setBody("撒撒打算打算打算打算打算打算的撒打算打算的");
            message.setMsgId(UUID.randomUUID().toString());


            channelFuture.channel().writeAndFlush(message).addListener(new ChannelFutureListener(){
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("发送成功");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
