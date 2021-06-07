package com.hsmq.server.handle;

import com.hsmq.protocol.HsBaseData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class ServerInHandel extends ChannelInboundHandlerAdapter {

    /**
     * 已添加处理程序
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerAdded 已添加处理程序");
        super.handlerAdded(ctx);
    }

    /**
     * 渠道注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered 渠道注册");
        super.channelRegistered(ctx);
    }

    /**
     * 通道活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive 通道活动");
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
        System.out.println(id+" channelRead 信道读取");

        //解码放在解码器里面
        System.out.println(id+" 请求:"+msg);

        HsBaseData hsBaseData = new HsBaseData("服务的端的返回");
        ctx.channel().writeAndFlush(hsBaseData).sync();

        super.channelRead(ctx, msg);
    }

    /**
     * 通道读取完成
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete 通道读取完成");
        super.channelReadComplete(ctx);
    }

    /**
     * 通道未激活
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive 通道未激活");
        super.channelInactive(ctx);
    }

    /**
     * 渠道未注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered 渠道未注册");
        super.channelUnregistered(ctx);
    }

    /**
     * handler被删除事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved handler删除事件");
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
        System.out.println("userEventTriggered 已触发用户事件");
        super.userEventTriggered(ctx, evt);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught 捕获到异常");
        super.exceptionCaught(ctx, cause);
    }

}
