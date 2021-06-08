package com.hsms.mqserver.handle;

import com.hsmq.data.Message;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.ResultEnum;
import com.hsms.mqserver.data.MessageStore;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @author: 河神
 * @date:2020-04-18
 */
public class ServerInHandel extends SimpleChannelInboundHandler<Message> {

    private static MessageStore messageStore ;

    static {
        messageStore = new MessageStore();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        ChannelId id = ctx.channel().id();
        //解码放在解码器里面
//        System.out.println("["+id+"] 请求:"+msg.getType());

        Message result ;
        if (MessageEnum.Pull.getCode().equals(msg.getType())){
            result = messageStore.pull(msg);
        }else{
            //处理消息
            messageStore.save(msg);
            //返回消息
            result = okResult();
        }
        ctx.channel().writeAndFlush(result).sync();
    }

    private Message okResult(){
        Message message = new Message();
        message.setMsgId(UUID.randomUUID().toString());
        message.setType(MessageEnum.Result.getCode());
        message.setResult(ResultEnum.SendOK.getCode());
        return message;
    }

}
