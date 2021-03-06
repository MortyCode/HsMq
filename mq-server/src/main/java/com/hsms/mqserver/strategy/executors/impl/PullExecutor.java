package com.hsms.mqserver.strategy.executors.impl;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.Pull;
import com.hsmq.data.message.PullMessage;
import com.hsmq.data.message.PullMessageResp;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;
import com.hsms.mqserver.data.ConsumerQueueManger;
import com.hsms.mqserver.data.TopicListener;
import com.hsms.mqserver.strategy.executors.BaseExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author ：河神
 * @date ：Created in 2021/6/9 1:50 下午
 */
public class PullExecutor extends BaseExecutor<Pull> {

    final static Logger log = LoggerFactory.getLogger(PullExecutor.class);

    @Override
    public HsResp<?> executor(HsReq<Pull> hsReq) {

        Pull pull = hsReq.getData();

        TopicListener topicListener = ConsumerQueueManger.getTopicListener(pull.getTopic());
        if (topicListener==null){
            return HsResp.typeError();
        }

        List<PullMessage> pullMessages = topicListener.pullMessage(pull);
        PullMessageResp pullMessageResp = new PullMessageResp();
        pullMessageResp.setPullMessages(pullMessages);
        pullMessageResp.setTopic(pull.getTopic());
        pullMessageResp.setQueueId(pull.getQueueId());
        pullMessageResp.setConsumerGroup(pull.getConsumerGroup());
        if (pullMessages!=null){
            Optional<Long> first = pullMessages.stream().map(PullMessage::getIndex).max(Comparator.comparing(Long::longValue));
            first.ifPresent(pullMessageResp::setLastIndex);
        }

        HsResp<PullMessageResp> resp = new HsResp<>();
        resp.setData(pullMessageResp);
        resp.setOperation(OperationEnum.Resp.getOperation());
        resp.setResult(ResultEnum.SendOK.getCode());
        return resp;
    }

    @Override
    public HsReq<Pull> convertReq(HsReq<?> hsReq) {
        if (hsReq.getData() instanceof Pull){
            HsReq<Pull> data = new HsReq<>();
            data.setData((Pull)hsReq.getData());
            data.setOperation(hsReq.getOperation());
            return data;
        }
        return null;
    }
}
