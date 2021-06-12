package com.hsmq.data;

import com.hsmq.enums.MessageEnum;
import com.hsmq.utils.ObjectByteUtils;

import java.io.Serializable;

/**
 * @author ：河神
 * @date ：Created in 2021/6/8 8:26 下午
 */
public class Head  implements Serializable {

    private static final long serialVersionUID = -20210610L;


    /**
     * @see MessageEnum
     * 消息类型：Req 和 Resp
     */
    private String msgType;

    public String getMsgType() {
        return msgType;
    }

    public MessageEnum getMsgTypeEnum() {
        return MessageEnum.getByCode(msgType);
    }


    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }


    public static Head toHead(byte[] headData){
        return (Head) ObjectByteUtils.toObject(headData);
    }

    public static Head toHead(MessageEnum messageEnum){
        Head head = new Head();
        head.setMsgType(messageEnum.getCode());
        return head;
    }
}
