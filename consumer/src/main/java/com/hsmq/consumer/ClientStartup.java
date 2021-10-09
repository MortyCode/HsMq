package com.hsmq.consumer;

import com.hsmq.consumer.config.RegisteredConsumer;
import com.hsmq.consumer.consumer.ConsumerHandlerManger;
import com.hsmq.consumer.executos.ExecutorMessageTask;
import com.hsmq.consumer.reactor.ConsumerClient;
import io.netty.channel.ChannelFuture;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:25 下午
 */
public class ClientStartup {

    final static Logger log = LoggerFactory.getLogger(ClientStartup.class);


    public static void main(String[] args) throws InterruptedException {

        String start =
                "\n" +
                        " _   _  _____        _____ _ _            _   \n" +
                        "| | | |/  ___|      /  __ \\ (_)          | |  \n" +
                        "| |_| |\\ `--. ______| /  \\/ |_  ___ _ __ | |_ \n" +
                        "|  _  | `--. \\______| |   | | |/ _ \\ '_ \\| __|\n" +
                        "| | | |/\\__/ /      | \\__/\\ | |  __/ | | | |_ \n" +
                        "\\_| |_/\\____/        \\____/_|_|\\___|_| |_|\\__|\n";
        log.info(start);
        RegisteredConsumer.setStopWatch(new StopWatch());

        ConsumerClient baseConsumer = new ConsumerClient("127.0.0.1", 9001);
        baseConsumer.start();

        //设置消费端名称
        ConsumerHandlerManger.setConsumerName("AConsumer");
        //初始化消费者
        ConsumerHandlerManger.initConsumer();
        //初始化任务
        ChannelFuture channelFuture = baseConsumer.getChannelFuture();

        RegisteredConsumer.setChannelFuture(channelFuture);
        for (String topic : args) {
            //注册对应消费者的任务
            RegisteredConsumer.registeredConsumer(topic);
        }
        RegisteredConsumer.initConsumerQueue();
    }

}
