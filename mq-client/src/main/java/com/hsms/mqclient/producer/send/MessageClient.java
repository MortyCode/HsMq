package com.hsms.mqclient.producer.send;

import com.hsmq.data.Head;
import com.hsmq.data.HsReq;
import com.hsmq.data.HsResp;
import com.hsmq.data.message.SendMessage;
import com.hsmq.enums.MessageEnum;
import com.hsmq.enums.OperationEnum;
import com.hsmq.protocol.HsEecodeData;
import com.hsms.mqclient.producer.dto.SendMessageResult;
import io.netty.channel.ChannelFuture;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：河神
 * @date ：Created in 2021/10/9 11:08 下午
 */
public class MessageClient {

    private static  ChannelFuture channelFuture;

    private static ConcurrentHashMap<Integer, SendMessageResult> resultMap = new ConcurrentHashMap<>();

    private static AtomicInteger reqId = new AtomicInteger(0);

    public static void setChannelFuture(ChannelFuture channelFuture) {
        MessageClient.channelFuture = channelFuture;
    }

    public static void executorResp(HsResp<?> data){
        SendMessageResult sendMessageResult = resultMap.get(data.getReqId());
        if (sendMessageResult ==null){
            return;
        }
        sendMessageResult.setMsgId((String) data.getData());
        sendMessageResult.setMessageResult(data.getResult());
        sendMessageResult.setRespDesc("发送成功");

        resultMap.remove(data.getReqId());
    }

    public static SendMessageResult sendMsg(SendMessage sendMessage){
        SendMessageResult sendMessageResult = new SendMessageResult();
        try {
            HsEecodeData hsEecodeData = new HsEecodeData();
            hsEecodeData.setHead(Head.toHead(MessageEnum.Req));

            HsReq<SendMessage> hsReq = new HsReq<>();
            hsReq.setOperation(OperationEnum.SendMessage.getOperation());
            hsReq.setData(sendMessage);
            hsReq.setReqId(reqId.incrementAndGet());
            hsEecodeData.setData(hsReq);

            resultMap.put(hsReq.getReqId(), sendMessageResult);

            MessageClient.channelFuture
                    .channel()
                    .writeAndFlush(hsEecodeData)
                    .addListener((future)->{
                        if (future.isSuccess()) {
                            sendMessageResult.setSendDone(true);
                        } else {
                            sendMessageResult.setSendDone(false);
                            sendMessageResult.setRespDesc("消息发送失败");
                        }
                    });

            long nanosTimeout = TimeUnit.SECONDS.toNanos(3);
            final long deadline = System.nanoTime() + nanosTimeout;

            while (true) {
                if (nanosTimeout<0){
                    sendMessageResult.setRespDesc("发送超时");
                    sendMessageResult.setMessageResult(-1);
                    break;
                }
                if (sendMessageResult.getMessageResult() != null) {
                    break;
                }
                nanosTimeout = deadline - System.nanoTime();
            }
            return sendMessageResult;
        } catch (Exception e) {
            sendMessageResult.setMessageResult(-2);
            sendMessageResult.setSendDone(false);
            e.printStackTrace();
        }
        return sendMessageResult;
    }


}
