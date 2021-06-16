package com.hsms.mqserver.strategy.executors;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.Message;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 6:18 下午
 */
public class ConfirmExecutor extends BaseExecutor<Message,String>{

    @Override
    public HsResp<String> executor(HsReq<Message> hsReq) {



        return null;
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
