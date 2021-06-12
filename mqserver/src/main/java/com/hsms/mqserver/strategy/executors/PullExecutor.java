package com.hsms.mqserver.strategy.executors;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.Message;
import com.hsmq.data.Pull;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:50 下午
 */
public class PullExecutor extends BaseExecutor<Pull,Message>{

    @Override
    public HsResp<Message> executor(HsReq<Pull> hsReq) {

        Pull pull = hsReq.getData();
        Message message = messageStore.pullMessage(pull);

        HsResp<Message> resp = new HsResp<>();
        resp.setData(message);
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
