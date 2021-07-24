package com.hsmq.encode;

import com.hsmq.data.message.SendMessage;
import com.hsmq.protocol.HsMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author ：河神
 * @date ：Created in 2021/6/6 6:38 下午
 */
public class ObjectEncode extends MessageToByteEncoder<SendMessage> {


    @Override
    protected void encode(ChannelHandlerContext ctx, SendMessage msg, ByteBuf out) throws Exception {

        HsMessage<SendMessage> hsMessage = new HsMessage<>(msg);

        out.writeInt(hsMessage.getLength());
        out.writeBytes(hsMessage.getDataArray());
    }
}
