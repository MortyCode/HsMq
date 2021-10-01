package com.hsms.mqserver.handle;

import com.hsmq.protocol.HsDecodeData;
import com.hsmq.protocol.HsEecodeData;
import com.hsms.mqserver.strategy.MessageStrategy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class ServerInHandel extends SimpleChannelInboundHandler<HsDecodeData> {

    final static Logger log = LoggerFactory.getLogger(ServerInHandel.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData decodeData) throws Exception {
        if (decodeData==null){
            log.warn("decodeData is null");
            return;
        }
        HsEecodeData executor = MessageStrategy.executor(decodeData);
        ctx.channel().writeAndFlush(executor).sync();
    }

}
