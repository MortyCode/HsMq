package com.hsms.mqserver.data;

import com.hsmq.data.HsResp;
import com.hsmq.data.message.SendMessage;
import com.hsmq.data.message.Pull;
import com.hsmq.enums.OperationEnum;
import com.hsmq.enums.ResultEnum;
import com.hsmq.storage.data.MessageStorage;
import com.hsmq.storage.durability.MessageDurability;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;

/**
 * @author ：河神
 * @date ：Created in 2021/6/7 11:10 下午
 */
public class MessageStore {

    private final static InternalLogger logger = InternalLoggerFactory.getInstance(MessageStorage.class);
//    private static ConcurrentHashMap<String,ConcurrentLinkedQueue<Message>> data = new ConcurrentHashMap<>();

    private final ConsumerQueueManger consumerQueueManger = new ConsumerQueueManger();
    private final MessageStorage messageStorage = new MessageStorage();


    public List<SendMessage> pullMessage(Pull pull){
        ConsumerQueue consumerQueue = consumerQueueManger.getAndRegister(pull);
        if (consumerQueue==null){
            return null;
        }

        return consumerQueue.pullMessage(pull);
    }

    public HsResp<?> saveMessage(SendMessage sendMessage){

        boolean existsTopic = consumerQueueManger.existsTopic(sendMessage);
        if (!existsTopic){
            return HsResp.topicNotExistsError();
        }

        MessageDurability messageDurability = messageStorage.saveMessage(sendMessage);
        logger.info("saveMessage#messageDurability:{}",messageDurability);
        consumerQueueManger.pushConsumerQueue(sendMessage,messageDurability);

        HsResp<String> resp = new HsResp<>();
        resp.setData(sendMessage.getMsgId());
        resp.setOperation(OperationEnum.Resp.getOperation());
        resp.setResult(ResultEnum.SendOK.getCode());
        return resp;
    }

}
