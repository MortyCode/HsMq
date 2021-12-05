package com.hsms.mqclient;

import com.hsmq.data.message.SendMessage;
import com.hsms.mqclient.consumer.config.RegisteredConsumer;
import com.hsms.mqclient.consumer.consumer.ConsumerHandlerManger;
import com.hsms.mqclient.producer.dto.SendMessageResult;
import com.hsms.mqclient.producer.send.MessageClient;
import com.hsms.mqclient.reactor.ClientReactor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:25 下午
 */
public class ClientStartup {

    final static Logger log = LoggerFactory.getLogger(ClientStartup.class);


    public static void main(String[] args) throws InterruptedException, IOException {

        String start =
                "\n" +
                        " _   _  _____        _____ _ _            _   \n" +
                        "| | | |/  ___|      /  __ \\ (_)          | |  \n" +
                        "| |_| |\\ `--. ______| /  \\/ |_  ___ _ __ | |_ \n" +
                        "|  _  | `--. \\______| |   | | |/ _ \\ '_ \\| __|\n" +
                        "| | | |/\\__/ /      | \\__/\\ | |  __/ | | | |_ \n" +
                        "\\_| |_/\\____/        \\____/_|_|\\___|_| |_|\\__|\n";
        log.info(start);

        //记录启动时间
        RegisteredConsumer.setStopWatch();

        //注册Netty
        ClientReactor clientReactor = new ClientReactor("127.0.0.1", 9001);
        //启动Netty
        clientReactor.start();

        //初始化消费者
        args = new String[]{"TopicB"};
        ConsumerHandlerManger.initConsumer("BBAConsumer",args,clientReactor.getChannelFuture());
        ConsumerHandlerManger.initConsumer("BBCConsumer",args,clientReactor.getChannelFuture());

        //初始化生产者
        MessageClient.setChannelFuture(clientReactor.getChannelFuture());


        SendMessage sendMessage = new SendMessage();
        sendMessage.setTopic("TopicB");
        sendMessage.setTag("tagB");
        sendMessage.setBody("消息---1");

        for (int i=0;i<2;i++){
            SendMessageResult b = MessageClient.sendMsg(sendMessage);

            if (!Integer.valueOf(200).equals(b.getMessageResult())){
                System.out.println("消息发送失败 : "+b);
                break;
            }
            System.out.println("消息发送 : "+b);
            Thread.sleep(100L);
        }

        //发送消息
//        for (int i=5000;;i++){
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setTopic("TopicB");
//            sendMessage.setTag("tagB");
//            sendMessage.setBody("消息---"+i);
//            MessageClient.sendMsg(sendMessage);
//            Thread.sleep(100L);
//        }

    }

}
