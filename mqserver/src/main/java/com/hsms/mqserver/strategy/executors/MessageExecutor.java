package com.hsms.mqserver.strategy.executors;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.Message;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;

import java.util.Properties;
import java.util.UUID;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:50 下午
 */
public class MessageExecutor extends BaseExecutor<Message,String>{


    @Override
    public HsResp<String> executor(HsReq<Message> hsReq) {
        Message message = hsReq.getData();
        message.setMsgId(msgId(message));
        messageStore.saveMessage(message);

        HsResp<String> resp = new HsResp<>();
        resp.setData(message.getMsgId());
        resp.setOperation(OperationEnum.Resp.getOperation());
        resp.setResult(ResultEnum.SendOK.getCode());
        return resp;
    }

    private String msgId(Message message){
        return message.getTopic().hashCode()+UUID.randomUUID().toString();
    }


    @Override
    public HsResp<?> executor0(HsReq<?> hsReq) {
        if (hsReq.getData() instanceof Message){
            HsReq<Message> data = new HsReq<>();
            data.setData((Message)hsReq.getData());
            data.setOperation(hsReq.getOperation());
            return executor(data);
        }
        return HsResp.typeError();
    }

}
