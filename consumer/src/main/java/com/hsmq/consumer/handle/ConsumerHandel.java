package com.hsmq.consumer.handle;

import com.hsmq.data.HsResp;
import com.hsmq.data.Message;
import com.hsmq.enums.ResultEnum;
import com.hsmq.protocol.HsDecodeData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:56 下午
 */
public class ConsumerHandel extends SimpleChannelInboundHandler<HsDecodeData> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData msg) throws Exception {


        HsResp data = (HsResp) msg.getData();
        if (data.getData()!=null){
            System.out.println("消费："+data.getData());
        }

    }

}
