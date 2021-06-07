package com.hsmq.client.client;


import com.hsmq.client.encode.BaseEncode;
import com.hsmq.client.handle.ClientInHandel;
import com.hsmq.client.handle.ClientOutHandle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class BaseConsumer {

    private String host;
    private int port;
    private Bootstrap bootstrap;

    public BaseConsumer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        bootstrap = new Bootstrap();

        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        bootstrap.group(eventExecutors);

        bootstrap.channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new BaseEncode());
                pipeline.addLast(new ClientInHandel());
                pipeline.addLast(new ClientOutHandle());
            }
        });

    }

    public ChannelFuture getChannelFuture() throws InterruptedException {
        return bootstrap.connect(host, port).sync();
    }

}
