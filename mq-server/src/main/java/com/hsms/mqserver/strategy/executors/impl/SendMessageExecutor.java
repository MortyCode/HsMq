package com.hsms.mqserver.strategy.executors.impl;

import com.hsmq.common.utils.MessageIdUtils;
import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.SendMessage;
import com.hsms.mqserver.strategy.executors.BaseExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:50 下午
 */
public class SendMessageExecutor extends BaseExecutor<SendMessage> {

    Logger logger = LoggerFactory.getLogger(PullExecutor.class);

    @Override
    public HsResp<?> executor(HsReq<SendMessage> hsReq) {
        SendMessage sendMessage = hsReq.getData();
        sendMessage.setMsgId(MessageIdUtils.newMsgId(sendMessage.getTopic()));
        HsResp<?> hsResp = messageStore.saveMessage(sendMessage);
        hsResp.setReqId(hsReq.getReqId());
        return hsResp;
    }

    @Override
    public HsReq<SendMessage> convertReq(HsReq<?> hsReq) {
        if (hsReq.getData() instanceof SendMessage){
            HsReq<SendMessage> data = new HsReq<>();
            data.setData((SendMessage)hsReq.getData());
            data.setOperation(hsReq.getOperation());
            return data;
        }
        return null;
    }

}
