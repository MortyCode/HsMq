package com.hsms.mqclient.consumer.executos;

import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.message.SyncOffsetMessage;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.protocol.HsEecodeData;
import com.hsms.mqclient.ClientStartup;
import com.hsms.mqclient.consumer.config.RegisteredConsumer;
import com.hsms.mqclient.consumer.message.ConsumerMessageQueue;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 定时提交偏移量任务
 * @author ：河神
 * @date ：Created in 2021/9/30 8:42 下午
 */
public class CommitOffsetTask implements Runnable{

    final static Logger log = LoggerFactory.getLogger(ClientStartup.class);


    private final ConsumerMessageQueue consumerMessageQueue;
    private final ChannelFuture channelFuture;


    public CommitOffsetTask(ChannelFuture channelFuture,ConsumerMessageQueue consumerMessageQueue) {
        this.channelFuture = channelFuture;
        this.consumerMessageQueue = consumerMessageQueue;
    }

    @Override
    public void run() {
        Map<Integer, Long> offSetMap = consumerMessageQueue.getOffSetMap();
        log.info("consumerGroupL:{} ,offSetMap:{}",consumerMessageQueue.getConsumerGroup(),offSetMap);
        while (true){
            //尚未初始化完成
            if (!RegisteredConsumer.isInit()){
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            try {
                syncIndex();
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void syncIndex() throws InterruptedException {
        HsEecodeData hsEecodeData = new HsEecodeData();
        hsEecodeData.setHead(Head.toHead(MessageEnum.Req));

        HsReq<SyncOffsetMessage> hsReq = new HsReq<>();

        SyncOffsetMessage syncOffsetMessage = new SyncOffsetMessage();
        syncOffsetMessage.setTopic(consumerMessageQueue.getTopic());
        syncOffsetMessage.setConsumer(consumerMessageQueue.getConsumerGroup());
        syncOffsetMessage.setOffSetMap(consumerMessageQueue.getOffSetMap());

        hsReq.setData(syncOffsetMessage);
        hsReq.setOperation(OperationEnum.CommitOffset.getOperation());
        hsEecodeData.setData(hsReq);

        channelFuture.channel().writeAndFlush(hsEecodeData).sync();
    }
}
