package com.hsms.mqclient;

import com.alibaba.fastjson.JSON;
import com.hsms.mqclient.consumer.config.ClientConfig;
import com.hsms.mqclient.consumer.config.RegisteredConsumer;
import com.hsms.mqclient.consumer.consumer.ConsumerHandlerManger;
import com.hsms.mqclient.reactor.ClientReactor;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        RegisteredConsumer.setStopWatch(new StopWatch());

        //注册Netty
        ClientReactor clientReactor = new ClientReactor("127.0.0.1", 9001);
        //启动Netty
        clientReactor.start();
        //初始化消费者
        ConsumerHandlerManger.initConsumer("AConsumer",args,clientReactor.getChannelFuture());





        //发送消息
//        for (int i=5000;;i++){
//            HsEecodeData hsEecodeData = new HsEecodeData();
//            hsEecodeData.setHead(Head.toHead(MessageEnum.Req));
//
//            HsReq<SendMessage> hsReq = new HsReq<>();
//            SendMessage sendMessage = new SendMessage();
//
//            hsReq.setOperation(OperationEnum.SendMessage.getOperation());
//
//            sendMessage.setTopic("TopicB");
//            sendMessage.setTag("tagB");
//            sendMessage.setBody("消息---"+i);
//            hsReq.setData(sendMessage);
//            hsEecodeData.setData(hsReq);
//
//            Thread.sleep(100L);
//            clientReactor.getChannelFuture().channel().writeAndFlush(hsEecodeData).sync();
//        }

    }

}
