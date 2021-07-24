package com.hsms.mqserver.strategy.executors.impl;

import com.hsmq.common.utils.MessageIdUtils;
import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.SendMessage;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;
import com.hsms.mqserver.strategy.executors.BaseExecutor;

import java.util.UUID;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:50 下午
 */
public class SendMessageExecutor extends BaseExecutor<SendMessage> {


    @Override
    public HsResp<?> executor(HsReq<SendMessage> hsReq) {
        SendMessage sendMessage = hsReq.getData();
        sendMessage.setMsgId(MessageIdUtils.newMsgId(sendMessage.getTopic()));
        System.out.println(sendMessage.getMsgId());
        return messageStore.saveMessage(sendMessage);
    }

    @Override
    public HsResp<?> executor0(HsReq<?> hsReq) {
        if (hsReq.getData() instanceof SendMessage){
            HsReq<SendMessage> data = new HsReq<>();
            data.setData((SendMessage)hsReq.getData());
            data.setOperation(hsReq.getOperation());
            return executor(data);
        }
        return HsResp.typeError();
    }

}
