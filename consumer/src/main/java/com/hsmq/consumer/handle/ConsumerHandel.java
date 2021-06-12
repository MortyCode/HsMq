package com.hsmq.consumer.handle;

import com.hsmq.data.HsResp;
import com.hsmq.data.Message;
import com.hsmq.enums.ResultEnum;
import com.hsmq.protocol.HsDecodeData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:56 下午
 */
public class ConsumerHandel extends SimpleChannelInboundHandler<HsDecodeData> {

    private static InternalLogger logger = InternalLoggerFactory.getInstance(ConsumerHandel.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData msg) throws Exception {


        HsResp data = (HsResp) msg.getData();
        if (data.getData()!=null){
            logger.info("消费:{}：",data.getData());
        }

    }

}
