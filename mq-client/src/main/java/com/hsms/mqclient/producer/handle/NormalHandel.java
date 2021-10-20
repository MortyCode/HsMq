package com.hsms.mqclient.producer.handle;

import com.alibaba.fastjson.JSON;
import com.hsmq.data.HsResp;
import com.hsmq.protocol.HsDecodeData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:56 下午
 */
public class NormalHandel extends SimpleChannelInboundHandler<HsDecodeData> {

    final static Logger log = LoggerFactory.getLogger(NormalHandel.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HsDecodeData msg) throws Exception {
        HsResp<?> data = (HsResp<?>) msg.getData();
        System.out.println("普通返回");
        if (data.getData()!=null){
            if (data.getData() instanceof String){
                log.info("普通返回:{}：",JSON.toJSONString(msg.getData()));
            }
        }
    }

}
