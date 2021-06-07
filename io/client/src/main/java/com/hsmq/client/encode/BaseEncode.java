package com.hsmq.client.encode;

import com.hsmq.protocol.HsBaseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:38 下午
 */
public class BaseEncode extends MessageToByteEncoder<HsBaseData> {


    @Override
    protected void encode(ChannelHandlerContext ctx, HsBaseData msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getBody().getBytes());
        System.out.println("编码完成");
    }
}
