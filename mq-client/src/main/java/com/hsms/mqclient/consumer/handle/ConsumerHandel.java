package com.hsms.mqclient.consumer.handle;

import com.hsmq.data.HsResp;
import com.hsmq.data.message.MessageQueueData;
import com.hsmq.data.message.PullMessageResp;
import com.hsmq.enums.OperationEnum;
import com.hsmq.protocol.HsDecodeData;
import com.hsms.mqclient.consumer.config.RegisteredConsumer;
import com.hsms.mqclient.consumer.message.ConsumerMessageQueue;
import com.hsms.mqclient.producer.send.MessageClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:56 下午
 */
public class ConsumerHandel extends SimpleChannelInboundHandler<HsDecodeData> {

    final static Logger log = LoggerFactory.getLogger(ConsumerHandel.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData msg) throws Exception {
        HsResp<?> data = (HsResp<?>) msg.getData();
        if (data.getData()!=null){
            if (data.getData() instanceof PullMessageResp){
                PullMessageResp pullMessageResp = (PullMessageResp) data.getData();
                ConsumerMessageQueue queue = RegisteredConsumer.getConsumerMessageQueue(pullMessageResp.getTopic(),pullMessageResp.getConsumerGroup());
                if (queue!=null){
                    queue.addMessage(pullMessageResp);
                }else{
                    log.info("not search topic queue:{}",pullMessageResp.getTopic());
                }
            }
            if (data.getData() instanceof MessageQueueData){
                RegisteredConsumer.initConsumerQueueHandle((MessageQueueData)data.getData());
            }

            if (OperationEnum.SendMessage.getOperation().equals(data.getReqType())){
                System.out.println("发送消息成功");
                MessageClient.executorResp(data);
            }

        }
    }

}
