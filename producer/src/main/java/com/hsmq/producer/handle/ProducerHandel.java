package com.hsmq.producer.handle;

import com.alibaba.fastjson.JSON;
import com.hsmq.protocol.HsDecodeData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:56 下午
 */
public class ProducerHandel extends SimpleChannelInboundHandler<HsDecodeData> {

    final static Logger log = LoggerFactory.getLogger(ProducerHandel.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData decodeData) throws Exception {
        log.info("消息发送:{}：",JSON.toJSONString(decodeData.getData()));
    }

}
