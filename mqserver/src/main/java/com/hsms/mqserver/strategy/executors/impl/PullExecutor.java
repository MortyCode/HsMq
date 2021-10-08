package com.hsms.mqserver.strategy.executors.impl;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.Pull;
import com.hsmq.data.message.PullMessage;
import com.hsmq.data.message.PullMessageResp;
import com.hsmq.data.message.SendMessage;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;
import com.hsms.mqserver.strategy.executors.BaseExecutor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:50 下午
 */
public class PullExecutor extends BaseExecutor<Pull> {

    @Override
    public HsResp<?> executor(HsReq<Pull> hsReq) {

        Pull pull = hsReq.getData();

        List<PullMessage> pullMessages = messageStore.pullMessage(pull);

        Optional<Long> first = pullMessages.stream().map(PullMessage::getIndex).max(Comparator.comparing(Long::longValue));


        PullMessageResp pullMessageResp = new PullMessageResp();
        pullMessageResp.setPullMessages(pullMessages);
        pullMessageResp.setTopic(pull.getTopic());
        pullMessageResp.setQueueId(pull.getQueueId());
        first.ifPresent(pullMessageResp::setLastIndex);

        HsResp<PullMessageResp> resp = new HsResp<>();
        resp.setData(pullMessageResp);
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
