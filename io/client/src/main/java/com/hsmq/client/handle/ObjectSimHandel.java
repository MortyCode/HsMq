package com.hsmq.client.handle;

import com.hsmq.data.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 8:27 下午
 */
public class ObjectSimHandel extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChannelId id = ctx.channel().id();
        //解码放在解码器里面
        System.out.println("接受到：["+id+"] ： "+msg);

        Message message = new Message();
        message.setTopic("TopicA");
        message.setTag("TagA");
        message.setBody("服务端的返回1112空间上加大设计大赛看见了打算扣篮大赛看见了打算空间的撒了空间");
        message.setMsgId(UUID.randomUUID().toString());

//        ctx.channel().writeAndFlush(message).sync();
    }
}
