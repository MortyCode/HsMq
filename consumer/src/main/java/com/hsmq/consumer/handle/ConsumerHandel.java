package com.hsmq.consumer.handle;

import com.hsmq.consumer.message.ConsumerMessageQueue;
import com.hsmq.consumer.message.ConsumerMessageQueueManger;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.PullMessage;
import com.hsmq.protocol.HsDecodeData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:56 下午
 */
public class ConsumerHandel extends SimpleChannelInboundHandler<HsDecodeData> {

    private static InternalLogger logger = InternalLoggerFactory.getInstance(ConsumerHandel.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData msg) throws Exception {
        HsResp data = (HsResp) msg.getData();
        if (data.getDatas()!=null&&data.getDatas().size()>0){
            Object o = data.getDatas().get(0);
            if (o instanceof PullMessage){
                PullMessage pullMessage = (PullMessage) o;
                ConsumerMessageQueue queue = ConsumerMessageQueueManger.getQueue(pullMessage.getTopic());
                queue.addMessage(data.getDatas());
            }
        }

    }

}
