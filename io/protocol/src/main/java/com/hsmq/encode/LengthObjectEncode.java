package com.hsmq.encode;

import com.hsmq.protocol.HsEecodeData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:38 下午
 */
public class LengthObjectEncode extends MessageToByteEncoder<HsEecodeData> {


    @Override
    protected void encode(ChannelHandlerContext ctx, HsEecodeData encodeData, ByteBuf out) throws Exception {
        out.writeInt(encodeData.getLength());
        out.writeInt(encodeData.getHeadLength());
        out.writeBytes(encodeData.getHead());
        out.writeInt(encodeData.getDataLength());
        out.writeBytes(encodeData.getData());
    }
}
