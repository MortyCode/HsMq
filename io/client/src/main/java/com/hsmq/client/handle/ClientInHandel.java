package com.hsmq.client.handle;

import com.hsmq.protocol.HsBaseData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class ClientInHandel extends ChannelInboundHandlerAdapter {


    /**
     * 已添加处理程序
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandelClientA handlerAdded 已添加处理程序");
        super.handlerAdded(ctx);
    }

    /**
     * 渠道注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandelClientA channelRegistered 渠道注册");
        super.channelRegistered(ctx);
    }

    /**
     * 通道活动
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandelClientA channelActive 通道活动");
        super.channelActive(ctx);
    }


    //4
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("HandelClientA channelRead");

        ByteBuf byteBuf = (ByteBuf) msg;

        if (byteBuf.isReadable()){
            byte[] bytes = new byte[byteBuf.writerIndex()];
            byteBuf.readBytes(bytes);
            System.out.println(Thread.currentThread() +"HandelClientA"+new String(bytes));
        }

        HsBaseData hsBaseData = new HsBaseData("我很好END");

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
        System.out.println("HandelClientA channelReadComplete 通道读取完成");
        super.channelReadComplete(ctx);
    }

    /**
     * 通道未激活
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandelClientA channelInactive 通道未激活");
        super.channelInactive(ctx);
    }

    /**
     * 渠道未注册
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandelClientA channelUnregistered 渠道未注册");
        super.channelUnregistered(ctx);
    }

    /**
     * handler被删除事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandelClientA handlerRemoved handler删除事件");
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
        System.out.println("HandelClientA userEventTriggered 已触发用户事件");
        super.userEventTriggered(ctx, evt);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("HandelClientA exceptionCaught 捕获到异常");
        super.exceptionCaught(ctx, cause);
    }


}
