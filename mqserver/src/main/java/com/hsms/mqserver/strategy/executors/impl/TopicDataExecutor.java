package com.hsms.mqserver.strategy.executors.impl;

import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.MessageQueueData;
import com.hsmq.data.message.PullMessageResp;
import com.hsmq.data.message.SendMessage;
import com.hsmq.data.message.TopicData;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;
import com.hsms.mqserver.data.ConsumerQueue;
import com.hsms.mqserver.data.ConsumerQueueManger;
import com.hsms.mqserver.data.TopicListener;
import com.hsms.mqserver.strategy.executors.BaseExecutor;

/**
 * @author ：河神
 * @date ：Created in 2021/10/2 3:54 下午
 */
public class TopicDataExecutor extends BaseExecutor<TopicData> {

    @Override
    public HsResp<?> executor(HsReq<TopicData> hsReq) {

        TopicData data = hsReq.getData();
        MessageQueueData messageQueueData = new MessageQueueData();

        TopicListener topicListener = ConsumerQueueManger.getTopicListener(data.getTopic());
        ConsumerQueue consumerQueue = topicListener.getConsumerQueue(data.getConsumerName());

        messageQueueData.setTopic(data.getTopic());
        messageQueueData.setQueueSize(consumerQueue.getQueueSize());

        HsResp<MessageQueueData> resp = new HsResp<>();
        resp.setData(messageQueueData);
        resp.setOperation(OperationEnum.Resp.getOperation());
        resp.setResult(ResultEnum.SendOK.getCode());
        return resp;

    }

    @Override
    public HsResp<?> executor0(HsReq<?> hsReq) {
        if (hsReq.getData() instanceof TopicData){
            HsReq<TopicData> data = new HsReq<>();
            data.setData((TopicData)hsReq.getData());
            data.setOperation(hsReq.getOperation());
            return executor(data);
        }
        return HsResp.typeError();
    }
}
