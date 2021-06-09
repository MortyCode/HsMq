package com.hsmq.decode;

import com.hsmq.data.Head;
import com.hsmq.enums.MessageEnum;
import com.hsmq.protocol.HsDecodeData;
import com.hsmq.protocol.HsMessage;
import com.hsmq.utils.ObjectAndByte;
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
public class LengthObjectDecode extends LengthFieldBasedFrameDecoder {


    private static final int FRAME_MAX_LENGTH =
            Integer.parseInt(System.getProperty("com.hsmq.frameMaxLength", "16777216"));

    public LengthObjectDecode() {
        super(FRAME_MAX_LENGTH, 0, 4, 0, 4);
    }

    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        ByteBuf frame = null;

        try {
            frame = (ByteBuf) super.decode(ctx, in);
            if (null == frame) {
                return null;

            }
            ByteBuffer byteBuffer = frame.nioBuffer();

            int limit = byteBuffer.limit();

            int headLength = byteBuffer.getInt();
            byte[] headData = new byte[headLength];
            byteBuffer.get(headData);

            Head head = Head.toHead(headData);
            if (head==null){
                return null;
            }

            int dataLength = byteBuffer.getInt();
            byte[] dataData = new byte[dataLength];
            byteBuffer.get(dataData);

            MessageEnum msgTypeEnum = head.getMsgTypeEnum();
            if (msgTypeEnum==null){
                return null;
            }

            HsDecodeData decodeData = new HsDecodeData();
            decodeData.setHead(head);
            decodeData.setData(ObjectAndByte.toObject(dataData));
            decodeData.setMsgTypeEnum(msgTypeEnum);

            return decodeData;
        }catch (Exception e){
            e.printStackTrace();
            ctx.channel().close();
        }finally {
            if (frame!=null){
                frame.release();
            }
        }
        return null;

    }

}
