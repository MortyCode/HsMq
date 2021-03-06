package com.mqclient;

import com.hsms.mqclient.consumer.config.RegisteredConsumer;
import com.hsms.mqclient.consumer.consumer.ConsumerHandlerManger;
import com.hsms.mqclient.reactor.ClientReactor;

/**
 * @author ：河神
 * @date ：Created in 2021/10/9 2:22 下午
 */
public class ClientB {


    public static void main(String[] args) throws InterruptedException {

        args = new String[]{"TopicA"};


        //记录启动时间
        RegisteredConsumer.setStopWatch();
        //注册Netty
        ClientReactor clientReactor = new ClientReactor("127.0.0.1", 9001);
        //启动Netty
        clientReactor.start();
        //初始化消费者
        ConsumerHandlerManger.initConsumer("RConsumer",args,clientReactor.getChannelFuture());

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
