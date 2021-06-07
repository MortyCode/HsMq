package com.hsmq.consumer;

import com.hsmq.client.client.BaseConsumer;
import com.hsmq.protocol.HsBaseData;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:25 下午
 */
public class ClientStartup {


    public static void main(String[] args) throws InterruptedException {
        BaseConsumer baseConsumer = new BaseConsumer("127.0.0.1", 9001);
        baseConsumer.start();

        ExecutorService executorService = Executors.newFixedThreadPool(2);


        executorService.execute(()->{
            ChannelFuture channelFuture = null;
            try {
                channelFuture = baseConsumer.getChannelFuture();
                HsBaseData hsBaseData = new HsBaseData("你好啊END");
                channelFuture.channel().writeAndFlush(hsBaseData).sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

}
