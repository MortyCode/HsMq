package com.hsms.mqserver.strategy.executors;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.SendMessage;
import com.hsmq.data.message.Pull;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;

import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:50 下午
 */
public class PullExecutor extends BaseExecutor<Pull, SendMessage>{

    @Override
    public HsResp<SendMessage> executor(HsReq<Pull> hsReq) {

        Pull pull = hsReq.getData();
        List<SendMessage> sendMessages = messageStore.pullMessage(pull);

        HsResp<SendMessage> resp = new HsResp<>();
        resp.setDatas(sendMessages);
        resp.setOperation(OperationEnum.Resp.getOperation());
        resp.setResult(ResultEnum.SendOK.getCode());
        return resp;
    }

    @Override
    public HsResp<?> executor0(HsReq<?> hsReq) {
        if (hsReq.getData() instanceof Pull){
            HsReq<Pull> data = new HsReq<>();
            data.setData((Pull)hsReq.getData());
            data.setOperation(hsReq.getOperation());
            return executor(data);
        }
        return HsResp.typeError();
    }
}
