package com.hsms.mqserver.handle;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.Message;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.ResultEnum;
import com.hsmq.protocol.HsDecodeData;
import com.hsmq.protocol.HsEecodeData;
import com.hsms.mqserver.data.MessageStore;
import com.hsms.mqserver.strategy.MessageStrategy;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Objects;
import java.util.UUID;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class ServerInHandel extends SimpleChannelInboundHandler<HsDecodeData> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData decodeData) throws Exception {
        if (decodeData==null){
            System.out.println("decodeData is null"+decodeData);
            return;
        }
        HsEecodeData executor = MessageStrategy.executor(decodeData);
        ctx.channel().writeAndFlush(executor).sync();
    }

}
