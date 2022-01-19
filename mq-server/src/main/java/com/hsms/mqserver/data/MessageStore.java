package com.hsms.mqserver.data;

import com.hsmq.data.HsResp;
import com.hsmq.data.message.SendMessage;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:10 下午
 */
public class MessageStore {

    final static Logger log = LoggerFactory.getLogger(MessageStore.class);

    public HsResp<?> saveMessage(SendMessage sendMessage){
        boolean existsTopic = ConsumerQueueManger.existsTopic(sendMessage);
        if (!existsTopic){
            return HsResp.topicNotExistsError();
        }

        MessageDurability messageDurability = MessageStorage.saveMessage(sendMessage);
        boolean push = ConsumerQueueManger.pushConsumerQueue(sendMessage, messageDurability);
        if (!push){
            return HsResp.topicNotExistsError();
        }
        log.info("saveMessage#messageDurability:{}",messageDurability);
        HsResp<String> resp = new HsResp<>();
        resp.setData(sendMessage.getMsgId());
        resp.setOperation(OperationEnum.Resp.getOperation());
        resp.setResult(ResultEnum.SendOK.getCode());
        return resp;
    }

}
