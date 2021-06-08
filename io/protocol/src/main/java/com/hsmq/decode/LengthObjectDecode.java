package com.hsmq.decode;

import com.hsmq.protocol.HsMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author: 河神
 * @date:2020-04-19
 */
public class LengthObjectDecode<T> extends LengthFieldBasedFrameDecoder {


    private static final int FRAME_MAX_LENGTH =
            Integer.parseInt(System.getProperty("com.hsmq.frameMaxLength", "16777216"));

    public LengthObjectDecode() {
        super(FRAME_MAX_LENGTH, 0, 4, 0, 4);
    }

    @Override
    public T decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (null == frame) {
            return null;
        }

        ByteBuffer byteBuffer = frame.nioBuffer();

        int length = byteBuffer.limit();
        byte[] bodyData = new byte[length];
        byteBuffer.get(bodyData);

        HsMessage<T> hsMessage = new HsMessage<>();
        hsMessage.appendArray(bodyData);
        return hsMessage.toData();
    }

}
