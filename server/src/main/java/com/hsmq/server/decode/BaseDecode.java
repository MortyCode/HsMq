package com.hsmq.server.decode;

import com.hsmq.protocol.HsBaseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 河神
 * @date:2020-04-19
 */
public class BaseDecode extends ReplayingDecoder<BaseDecode.Status> {

    enum Status {
        /**
         * 消息头,INT字符
         */
        head,
        /**
         * 消息体,字符串
         * 以结尾
         */
        body
    }

    private HsBaseData baseData;

    public BaseDecode() {
        super(Status.head);
        baseData = new HsBaseData();
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        b:while (byteBuf.isReadable()){
            switch (state()){
                case head:
                    int head = byteBuf.readInt();
                    System.out.println("decode--head------->" + head);
                    baseData.setLength(head);
                    checkpoint(Status.body);
                    break;
                case body:
                    byte[] b2 = new byte[baseData.getLength()];
                    byteBuf.readBytes(b2);
                    System.out.println("decode--b2------->" + new String(b2));
                    String data = new String(b2);
                    baseData.setBody(data);
                    if (data.endsWith("END")){
                        System.out.println("decode--b2 END");
                        checkpoint(Status.head);
                        break b;
                    }
                    break;
                default:
                    System.out.println("处理错误");
            }
        }

        if (baseData.isNotNull()){
            list.add(baseData.copy());
            baseData.setNull();
        }
    }
}
