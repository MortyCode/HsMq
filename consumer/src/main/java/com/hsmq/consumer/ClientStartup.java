package com.hsmq.consumer;

import com.hsmq.consumer.config.ExecutorService;
import com.hsmq.consumer.config.RegisteredConsumer;
import com.hsmq.consumer.executos.ExecutorMessageTask;
import com.hsmq.consumer.executos.PullMessageTask;
import com.hsmq.consumer.reactor.ConsumerClient;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:25 下午
 */
public class ClientStartup {


    public static void main(String[] args) throws InterruptedException {

        String start =
                "\n" +
                        " _   _  _____        _____ _ _            _   \n" +
                        "| | | |/  ___|      /  __ \\ (_)          | |  \n" +
                        "| |_| |\\ `--. ______| /  \\/ |_  ___ _ __ | |_ \n" +
                        "|  _  | `--. \\______| |   | | |/ _ \\ '_ \\| __|\n" +
                        "| | | |/\\__/ /      | \\__/\\ | |  __/ | | | |_ \n" +
                        "\\_| |_/\\____/        \\____/_|_|\\___|_| |_|\\__|\n";
        System.out.println(start);

        ConsumerClient baseConsumer = new ConsumerClient("127.0.0.1", 9001);
        baseConsumer.start();

        ChannelFuture channelFuture = baseConsumer.getChannelFuture();
        RegisteredConsumer registeredConsumer = new RegisteredConsumer(channelFuture);
        for (String topic : args) {
            registeredConsumer.registeredConsumer(topic);
        }


    }

}
