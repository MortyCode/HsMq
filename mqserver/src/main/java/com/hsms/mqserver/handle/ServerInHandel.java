package com.hsms.mqserver.handle;

import com.hsmq.protocol.HsDecodeData;
import com.hsmq.protocol.HsEecodeData;
import com.hsms.mqserver.strategy.MessageStrategy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class ServerInHandel extends SimpleChannelInboundHandler<HsDecodeData> {

    private static InternalLogger logger = InternalLoggerFactory.getInstance(ServerInHandel.class);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData decodeData) throws Exception {
        if (decodeData==null){
            logger.warn("decodeData is null");
            return;
        }
        HsEecodeData executor = MessageStrategy.executor(decodeData);
        ctx.channel().writeAndFlush(executor).sync();
    }

}
