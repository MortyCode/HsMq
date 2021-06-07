package com.hsmq.server.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class ServerOutHandle extends ChannelOutboundHandlerAdapter {


    /**
     * 服务端执行bind时
     * @param ctx
     * @param localAddress
     * @param promise
     * @throws Exception
     */
    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        System.out.println("HandleOutA bind 服务端执行bind时， ");
        super.bind(ctx, localAddress, promise);
    }

    /**
     * 客户端执行connect连接服务端时进入
     * @param ctx
     * @param remoteAddress
     * @param localAddress
     * @param promise
     * @throws Exception
     */
    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        System.out.println("HandleOutA connect connect连接服务端");
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    /**
     *
     * @param ctx
     * @param promise
     * @throws Exception
     */
    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        System.out.println("HandleOutA disconnect 连接断开");
        super.disconnect(ctx, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        System.out.println("HandleOutA close 连接关闭");
        super.close(ctx, promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        System.out.println("HandleOutA deregister");
        super.deregister(ctx, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandleOutA read");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("向服务端发送： "+msg);
        super.write(ctx,msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HandleOutA flush ");
        super.flush(ctx);
    }


}
