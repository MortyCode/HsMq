package com.hsms.mqclient.consumer.config;

import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.message.MessageQueueData;
import com.hsmq.data.message.TopicData;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.protocol.HsEecodeData;
import com.hsms.mqclient.consumer.executos.CommitOffsetTask;
import com.hsms.mqclient.consumer.executos.ExecutorMessageTask;
import com.hsms.mqclient.consumer.executos.PullMessageTask;
import com.hsms.mqclient.consumer.message.ConsumerMessageQueue;
import io.netty.channel.ChannelFuture;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：河神
 * @date ：Created in 2021/7/11 2:55 下午
 */
public class RegisteredConsumer {

    final static Logger log = LoggerFactory.getLogger(RegisteredConsumer.class);

    private static AtomicInteger initFlag = new AtomicInteger(0);
    private static ChannelFuture channelFuture;
    private static StopWatch stopWatch;
    private static Map<String, ConsumerMessageQueue> consumerMessageQueueMap = new HashMap<>();

    public static void setStopWatch(StopWatch stopWatch) {
        RegisteredConsumer.stopWatch = stopWatch;
        stopWatch.start();
    }

    public static Map<String, ConsumerMessageQueue> getConsumerMessageQueueMap() {
        return consumerMessageQueueMap;
    }

    public static ChannelFuture getChannelFuture() {
        return channelFuture;
    }


    public static void init(String[] topics,ChannelFuture channelFuture){
        RegisteredConsumer.channelFuture = channelFuture;
        for (String topic : topics) {
            RegisteredConsumer.registeredConsumer(topic);
        }
        RegisteredConsumer.initConsumerQueue();
    }

    public static void registeredConsumer(String topic){
        log.info("registeredConsumer topic:[{}]",topic);
        ThreadPoolExecutor executor = ExecutorService.getExecutor();
        //创建消费者
        ConsumerMessageQueue consumerMessageQueue = new ConsumerMessageQueue(topic);
        //注册到管理器中
        consumerMessageQueueMap.put(topic,consumerMessageQueue);
        //注册拉取消息任务
        executor.execute(new PullMessageTask(channelFuture , consumerMessageQueue));

        //注册执行器任务
        executor.execute(new ExecutorMessageTask(channelFuture ,consumerMessageQueue));

        //注册偏移量提交点
//        executor.execute(new CommitOffsetTask(channelFuture ,consumerMessageQueue));
        //定时任务
        channelFuture.channel().eventLoop().scheduleWithFixedDelay(()->{
            //定时任务
            new Thread(new CommitOffsetTask(channelFuture,consumerMessageQueue)).start();
        },1, 3L, TimeUnit.SECONDS);

        initFlag.incrementAndGet();
        log.info("registeredConsumer end consumer size :{} ",initFlag.get());
    }

    public static boolean isInit() {
        return initFlag.get()<=0;
    }

    public static void initConsumerQueue(){
        for (String topic : consumerMessageQueueMap.keySet()) {
            HsEecodeData hsEecodeData = new HsEecodeData();
            hsEecodeData.setHead(Head.toHead(MessageEnum.Req));
            HsReq<TopicData> hsReq = new HsReq<>();

            TopicData topicData = new TopicData();
            topicData.setTopic(topic);
            topicData.setConsumerName("AConsumer");

            hsReq.setData(topicData);
            hsReq.setOperation(OperationEnum.TopicData.getOperation());
            hsEecodeData.setData(hsReq);

            try {
                channelFuture.channel().writeAndFlush(hsEecodeData).sync();
                log.info("consumer init by req server -  topic:{}",topic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void initConsumerQueueHandle(MessageQueueData messageQueueData){
        ConsumerMessageQueue consumerMessageQueue = consumerMessageQueueMap.get(messageQueueData.getTopic());
        if (consumerMessageQueue==null){
            return;
        }
        //初始化消费者
        consumerMessageQueue.initQueue(messageQueueData);
        //设置初始化完成
        initFlag.decrementAndGet();

        if (isInit()){
            stopWatch.stop();
            log.info("Consumer Start End");
        }

    }

}
