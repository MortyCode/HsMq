package com.hsmq.decode;

import com.hsmq.protocol.HsMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author: 河神
 * @date:2020-04-19
 */
public class ObjectDecode<T> extends ByteToMessageDecoder {


    private HsMessage<T> hsMessage;

    public ObjectDecode() {
        this.hsMessage = new HsMessage<>();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {

        while (byteBuf.isReadable()&&byteBuf.readableBytes()>0) {
            if (hsMessage.getLength() == null) {
                if (byteBuf.readableBytes()>=4){
                    int head = byteBuf.readInt();
                    hsMessage.setLength(head);
                }else {
                    break;
                }
            } else {
                Integer length = hsMessage.getLength();
                if (hsMessage.getDataArray() != null) {
                    length = hsMessage.getLength() - hsMessage.getDataArray().length;
                }
                if (length>byteBuf.readableBytes()){
                    length = byteBuf.readableBytes();
                }
                byte[] b2 = new byte[length];
                byteBuf.readBytes(b2);
                hsMessage.appendArray(b2);
            }
            if (hsMessage.arrayFinish()) {
                out.add(hsMessage.toData());
                hsMessage.setNull();
                break;
            }
        }
    }
}
