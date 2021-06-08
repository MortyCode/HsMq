package com.hsmq.consumer.handle;

import com.hsmq.data.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:56 下午
 */
public class ConsumerHandel extends SimpleChannelInboundHandler<Message> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if (msg.getBody()==null){
            return;
        }
        System.out.println("消费："+msg);
    }

}
