package com.hsms.mqclient.reactor;


import com.hsmq.decode.LengthObjectDecode;
import com.hsmq.encode.LengthObjectEncode;
import com.hsms.mqclient.consumer.handle.ConsumerHandel;
import com.hsms.mqclient.producer.handle.NormalHandel;
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
public class ClientReactor {

    final static Logger log = LoggerFactory.getLogger(ClientReactor.class);


    private String host;
    private int port;
    private Bootstrap bootstrap;

    public ClientReactor(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start()  {
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
                pipeline.addLast(new NormalHandel());
            }
        });

    }

    public ChannelFuture getChannelFuture() throws InterruptedException {
        return bootstrap.connect(host, port).sync();
    }

}
