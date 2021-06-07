package com.hsmq.server.decode;

import com.hsmq.protocol.HsBaseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author: 河神
 * @date:2020-04-19
 */
public class MessageDecode extends ByteToMessageDecoder {


    private HsBaseData baseData;

    public MessageDecode() {
        this.baseData = new HsBaseData();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {

        while (byteBuf.isReadable()){
                if (baseData.getLength()==null){
                    int head = byteBuf.readInt();
                    System.out.println("decode--head------->" + head);
                    baseData.setLength(head);
                }else{
                    byte[] b2 = new byte[baseData.getLength()];
                    byteBuf.readBytes(b2);
                    System.out.println("decode--b2------->" + new String(b2));
                    String data = new String(b2);
                    baseData.appendBody(data);

                    if (data.endsWith("END")){
                        System.out.println("decode--b2 END");
                    }else {
                        continue;
                    }
            }
            if (baseData.isNotNull()){
                out.add(baseData.copy());
                baseData.setNull();
                break;
            }
        }
    }
}
