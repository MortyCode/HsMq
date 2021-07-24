package com.hsms.mqserver.strategy.executors;

import com.hsmq.common.utils.MessageIdUtils;
import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.SendMessage;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;

import java.util.UUID;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:50 下午
 */
public class SendMessageExecutor extends BaseExecutor<SendMessage,String>{


    @Override
    public HsResp<String> executor(HsReq<SendMessage> hsReq) {
        SendMessage sendMessage = hsReq.getData();
        sendMessage.setMsgId(MessageIdUtils.newMsgId(sendMessage.getTopic()));
        messageStore.saveMessage(sendMessage);


        HsResp<String> resp = new HsResp<>();
        resp.setData(sendMessage.getMsgId());
        resp.setOperation(OperationEnum.Resp.getOperation());
        resp.setResult(ResultEnum.SendOK.getCode());
        return resp;
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
