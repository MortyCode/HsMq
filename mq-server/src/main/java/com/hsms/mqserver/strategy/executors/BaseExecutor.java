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

    public abstract HsReq<T> convertReq(HsReq<?> hsReq);

    public HsEecodeData executorReq(HsReq<?> hsReq){
        HsReq<T> fixedHsReq = convertReq(hsReq);
        if (fixedHsReq==null){
            return HsEecodeData.typeError();
        }

        HsResp<?> hsResp = executor(fixedHsReq);
        hsResp.setReqType(hsReq.getOperation());
        hsResp.setReqId(hsReq.getReqId());
        return HsEecodeData.resp(hsResp);
    }

}
