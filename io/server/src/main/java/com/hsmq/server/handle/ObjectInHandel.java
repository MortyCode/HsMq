package com.hsmq.server.handle;

import com.hsmq.data.Message;
import com.hsmq.protocol.HsBaseData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class ObjectInHandel extends ChannelInboundHandlerAdapter {

    /**
     * 已添加处理程序
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
    }

    /**
     * 渠道注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    /**
     * 通道活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * 信道读取
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChannelId id = ctx.channel().id();

        //解码放在解码器里面
        System.out.println(id+" 请求:"+msg);

        Message message = new Message();
        message.setTopic("TopicA");
        message.setTag("TagA");
        message.setBody("服务端的返回1112空间上加大设计大赛看见了打算扣篮大赛看见了打算空间的撒了空间");
        message.setMsgId(UUID.randomUUID().toString());
        ctx.channel().writeAndFlush(message).sync();

        super.channelRead(ctx, msg);
    }

    /**
     * 通道读取完成
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 通道未激活
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    /**
     * 渠道未注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    /**
     * handler被删除事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }


    /**
     * 客户端的所有ChannelHandler中4s内没有write事件，则会触发userEventTriggered方法（
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
