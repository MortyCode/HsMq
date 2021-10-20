package com.hsms.mqclient.producer.dto;

import java.util.StringJoiner;

/**
 * @author ：河神
 * @date ：Created in 2021/10/10 2:20 下午
 */
public class SendMessageResult {


    /**
     * 是否消息发送成功
     */
    private volatile Integer messageResult;

    /**
     * 是否消息发送出去
     */
    private Boolean sendDone;

    /**
     * 消息id
     */
    private  String msgId;

    private String respDesc;

    public Integer getMessageResult() {
        return messageResult;
    }

    public void setMessageResult(Integer messageResult) {
        this.messageResult = messageResult;
    }

    public Boolean getSendDone() {
        return sendDone;
    }

    public void setSendDone(Boolean sendDone) {
        this.sendDone = sendDone;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SendMessageResult.class.getSimpleName() + "[", "]")
                .add("messageResult=" + messageResult)
                .add("sendDone=" + sendDone)
                .add("msgId='" + msgId + "'")
                .add("respDesc='" + respDesc + "'")
                .toString();
    }
}
