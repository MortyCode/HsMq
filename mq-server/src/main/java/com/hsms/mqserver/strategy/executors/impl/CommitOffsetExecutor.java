package com.hsms.mqserver.strategy.executors.impl;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.SyncOffsetMessage;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;
import com.hsmq.storage.data.QueueOffsetStorage;
import com.hsms.mqserver.strategy.executors.BaseExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/6/12 6:18 下午
 */
public class CommitOffsetExecutor extends BaseExecutor<SyncOffsetMessage> {

    final static Logger log = LoggerFactory.getLogger(CommitOffsetExecutor.class);


    @Override
    public HsResp<?> executor(HsReq<SyncOffsetMessage> hsReq) {
        SyncOffsetMessage data = hsReq.getData();
        QueueOffsetStorage.saveConsumer(data.getTopic(),data.getConsumer(), data.getOffSetMap());

        log.info("CommitOffsetExecutor sync offset:{}",data.getOffSetMap());

        HsResp<String> resp = new HsResp<>();
        resp.setData("OK");
        resp.setOperation(OperationEnum.CommitOffset.getOperation());
        resp.setResult(ResultEnum.SendOK.getCode());
        return resp;
    }

    @Override
    public HsReq<SyncOffsetMessage> convertReq(HsReq<?> hsReq) {
        if (hsReq.getData() instanceof SyncOffsetMessage){
            HsReq<SyncOffsetMessage> data = new HsReq<>();
            data.setData((SyncOffsetMessage)hsReq.getData());
            data.setOperation(hsReq.getOperation());
            return data;
        }
        return null;
    }
}
