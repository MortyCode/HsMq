package com.hsms.mqserver.strategy.executors;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.protocol.HsEecodeData;
import com.hsms.mqserver.data.MessageStore;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:52 下午
 */
public abstract class BaseExecutor<T> {

    public static MessageStore messageStore = new MessageStore();

    public abstract HsResp<?> executor(HsReq<T> hsReq);

    public abstract HsResp<?> executor0(HsReq<?> hsReq);

    public HsEecodeData executorReq(HsReq<?> hsReq){
        HsResp<?> hsResp = this.executor0(hsReq);
        hsResp.setReqType(hsReq.getOperation());
        hsResp.setReqId(hsReq.getReqId());
        return HsEecodeData.resp(hsResp);
    }

}
