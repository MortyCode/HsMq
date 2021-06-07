package com.hsmq.client.encode;

import com.hsmq.data.Message;
import com.hsmq.protocol.HsMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:38 下午
 */
public class ObjectEncode extends MessageToByteEncoder<Message> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        HsMessage<Message> hsMessage = new HsMessage<>(msg);

        out.writeInt(hsMessage.getLength());
        out.writeBytes(hsMessage.getDataArray());
        System.out.println("编码完成");
    }
}
