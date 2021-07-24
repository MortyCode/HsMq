package com.hsmq.producer.handle;

import com.alibaba.fastjson.JSON;
import com.hsmq.protocol.HsDecodeData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:56 下午
 */
public class ProducerHandel extends SimpleChannelInboundHandler<HsDecodeData> {

    private static InternalLogger logger = InternalLoggerFactory.getInstance(ProducerHandel.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData decodeData) throws Exception {
        logger.info("消息发送:{}：",JSON.toJSONString(decodeData.getData()));
    }

}
