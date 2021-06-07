package com.hsmq.client.client;


import com.hsmq.client.handle.ObjectInHandel;
import com.hsmq.client.handle.ObjectSimHandel;
import com.hsmq.data.Message;
import com.hsmq.decode.ObjectDecode;
import com.hsmq.encode.ObjectEncode;
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
public class ObjectClient {

    private String host;
    private int port;
    private Bootstrap bootstrap;

    public ObjectClient(String host, int port) {
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
                pipeline.addLast(new ObjectEncode());
                pipeline.addLast(new ObjectDecode<Message>());
                pipeline.addLast(new ObjectSimHandel());
            }
        });

    }

    public ChannelFuture getChannelFuture() throws InterruptedException {
        return bootstrap.connect(host, port).sync();
    }

}
