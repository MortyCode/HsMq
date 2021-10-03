package com.hsmq.consumer.reactor;


import com.hsmq.consumer.consumer.sub.TopicBConsumer;
import com.hsmq.consumer.handle.ConsumerHandel;
import com.hsmq.decode.LengthObjectDecode;
import com.hsmq.encode.LengthObjectEncode;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class ConsumerClient {

    final static Logger log = LoggerFactory.getLogger(ConsumerClient.class);


    private String host;
    private int port;
    private Bootstrap bootstrap;

    public ConsumerClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws InterruptedException {
        log.info("ConsumerClient#start");
        bootstrap = new Bootstrap();

        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true);


        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new LengthObjectEncode());
                pipeline.addLast(new LengthObjectDecode());
                pipeline.addLast(new ConsumerHandel());
            }
        });

    }

    public ChannelFuture getChannelFuture() throws InterruptedException {
        return bootstrap.connect(host, port).sync();
    }

}
